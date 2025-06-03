import atexit
from contextlib import contextmanager
from dataclasses import dataclass

from psycopg import Connection, connect

from app.conf import conf
from app.singleton import Singleton


@dataclass
class ConnectionContext(metaclass=Singleton):
    _connection: Connection | None = None

    def __post_init__(self):
        if self._connection is None:
            conn = connect(conninfo=conf.db_url, autocommit=True)
            object.__setattr__(self, "_connection", conn)
            atexit.register(conn.close)

    @contextmanager
    def cursor(self):
        with self._connection.cursor() as cursor:
            yield cursor


connection = ConnectionContext()
