from app.aws import s3
from app.conf import conf
from app.dbclient import connection


def download_file_as_bytes(object_key: str) -> bytes:
    response = s3.get_object(Bucket=conf.bucket_name, Key=object_key)
    return response["Body"].read()


def read_text_file(file_id: int):
    with connection.cursor() as cur:
        object_key = cur.execute("select object_key from files where id = %(id)s", {"id": file_id}).fetchone()[0]
        data = download_file_as_bytes(object_key)
        return data.decode("utf-8")
