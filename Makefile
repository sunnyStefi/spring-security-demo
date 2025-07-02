## 01 authentication-flow
authentication-flow:
	curl -vu user:7359e75a-565b-4798-8b7b-ba0226f42fb1 http://localhost:8080/private

## 02 custom-users
custom-users:
	curl -vu rickGrimes:sheriff123 http://localhost:8080/private

## 3 custom-filter
custom-filter:
	curl -vu rickGrimes:sheriff123 -H "x-safe-zone:123" http://localhost:8080/private

## 4 custom-provider
custom-provider:
	curl -H "x-safe-zone:123" http://localhost:8080/private

## 5 configurer
configurer-basic:
	curl -vu rickGrimes:sheriff1234 http://localhost:8080/private
