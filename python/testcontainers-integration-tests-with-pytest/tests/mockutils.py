import sys
from contextlib import contextmanager
from types import ModuleType


@contextmanager
def mock_module(module_name: str, **attributes):
    original_module = sys.modules.get(module_name)
    mocked_module = ModuleType(module_name)
    for name, value in attributes.items():
        setattr(mocked_module, name, value)
    sys.modules[module_name] = mocked_module

    try:
        yield
    finally:
        if original_module is not None:
            sys.modules[module_name] = original_module
        else:
            sys.modules.pop(module_name, None)
