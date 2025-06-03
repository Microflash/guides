import json
import os
from dataclasses import dataclass

from app.aws import secretsmanager
from app.singleton import Singleton


@dataclass(frozen=True)
class Configuration(metaclass=Singleton):
    bucket_name: str | None = None
    db_url: str | None = None

    def __post_init__(self):
        if self.bucket_name is None:
            object.__setattr__(self, "bucket_name", os.environ.get("APP_BUCKET_NAME"))
        if self.db_url is None:
            db_name = os.environ.get("APP_DB_NAME")
            db_user = os.environ.get("APP_DB_USER")
            db_host = os.environ.get("APP_DB_HOST")
            db_port = os.environ.get("APP_DB_PORT")
            db_secret = os.environ.get("APP_DB_SECRET")
            secret = json.loads(secretsmanager.get_secret_value(SecretId=db_secret)["SecretString"])
            object.__setattr__(
                self,
                "db_url",
                f"dbname={db_name} user={db_user} password={secret['password']} host={db_host} port={db_port}",
            )


conf = Configuration()
