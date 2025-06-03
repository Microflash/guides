import json
import os

import pytest
from testcontainers.localstack import LocalStackContainer
from testcontainers.postgres import PostgresContainer

from tests.mockutils import mock_module

object_key = "/root/text.txt"
test_content = b"Hello from Testcontainers!"


@pytest.fixture(scope="module", autouse=True)
def setup():
    with (
        LocalStackContainer(image="localstack/localstack:4.4.0") as localstack,
        PostgresContainer(image="postgres:17-alpine") as postgres,
        mock_module("app.aws", s3=localstack.get_client("s3"), secretsmanager=localstack.get_client("secretsmanager")),
    ):
        os.environ["APP_BUCKET_NAME"] = "test-bucket"
        os.environ["APP_DB_NAME"] = postgres.dbname
        os.environ["APP_DB_USER"] = postgres.username
        os.environ["APP_DB_HOST"] = postgres.get_container_host_ip()
        os.environ["APP_DB_PORT"] = str(postgres.get_exposed_port(5432))
        secret_name = "db/secret"
        os.environ["APP_DB_SECRET"] = secret_name

        from app.aws import s3, secretsmanager

        secretsmanager.create_secret(
            Name=secret_name,
            SecretString=json.dumps({"password": postgres.password}),
        )

        from app.conf import conf

        s3.create_bucket(
            Bucket=conf.bucket_name,
            CreateBucketConfiguration={"LocationConstraint": localstack.region_name},
        )

        from app.dbclient import connection

        with connection.cursor() as cursor:
            cursor.execute(
                """
                create table files (
                    id int generated always as identity primary key, 
                    object_key text not null
                );
                """
            )
        yield


def test_read_text_file():
    from app.dbclient import connection

    with connection.cursor() as cursor:
        from app.aws import s3
        from app.conf import conf

        s3.put_object(Bucket=conf.bucket_name, Key=object_key, Body=test_content)
        file_id = cursor.execute(
            """
            insert into files (object_key) values (%(objectKey)s) returning id
            """,
            {"objectKey": object_key},
        ).fetchone()[0]

        from app.main import read_text_file

        assert read_text_file(file_id) == test_content.decode("utf-8")
