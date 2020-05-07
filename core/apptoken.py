import datetime

token = datetime.datetime.now().hour**10
token = token*1012
token = token**2
token = token/23

print(token)

def verify(tokentoverify):
    if int(tokentoverify) == token:
        return True
    else:
        return False