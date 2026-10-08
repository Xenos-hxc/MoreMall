"""Exercise initialized demo services, including MongoDB queries and gateway routing."""
import json, os, time, urllib.request, urllib.parse
base = os.environ.get('SMOKE_BASE_URL', 'http://127.0.0.1:9088')
gateway = os.environ.get('SMOKE_GATEWAY_URL', 'http://127.0.0.1:10010/api/demoservice')
def request(path, form=None, prefix=base):
    data = urllib.parse.urlencode(form).encode() if form is not None else None
    req = urllib.request.Request(prefix + path, data=data)
    with urllib.request.urlopen(req, timeout=10) as response:
        return json.load(response)
for attempt in range(120):
    try:
        result = request('/staff/login', {'id': 'admin', 'password': 'local-demo-change-me'})
        if result.get('code') == 200:
            break
    except (OSError, ValueError):
        pass
    time.sleep(1)
else:
    raise RuntimeError('Business service did not become ready with the demo account')
assert request('/staff/login', {'id': 'admin', 'password': 'invalid-demo-password'})['code'] == 400
assert request('/product/listAllData')['code'] == 200
assert request('/product/getmsg')['code'] == 200
assert isinstance(request('/users/page?page=1&size=10'), list)
for attempt in range(120):
    try:
        if request('/product/listAllData', prefix=gateway).get('code') == 200:
            break
    except (OSError, ValueError):
        pass
    time.sleep(1)
else:
    raise RuntimeError('Gateway did not discover the business service')
print('Demo login, invalid login, product/member queries, dashboard and gateway passed.')
