demo-basic-public:
	curl -u user:dafc08db-207e-4c38-83f1-c98d8eba82c6 http://localhost:8080/public

demo-basic-private:
	curl -vu user:ce5e2047-e3d1-4061-bb36-53466a2fe8cf http://localhost:8080/private

demo-tea-header:
	curl -v -u banana:123 -H "x-tea: yes" http://localhost:8080/private

demo-user-header-401:
	curl -v -u banana:12345 -H "x-tea: yes" http://localhost:8080/private

demo-superman:
	curl -v -u superman: -H "x-tea: yes" http://localhost:8080/private

demo-tea-password-authenticated:
	curl -v -H "x-tea: yes" -H "x-tea-password: teaTime" http://localhost:8080/private

demo-tea-password-unauthenticated:
	curl -v -H "x-tea: yes" -H "x-tea-password: coffeeTime" http://localhost:8080/private