x = int(input())
b = bool(input())


class C:
    def __init__(self):
        pass

    def __add__(self, other):
        return self


c = C()

_ = x + 1 + 2
_ = b + 1 + 2
_ = c + 1 + 2
