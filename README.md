# LoanProcessCheck

**Camunda Console**

http://localhost:8282/

**Start Loan Process**

curl --location 'http://localhost:8282/api/startLoanProcessByType' \
--header 'Content-Type: application/json' \
--data '{
"name":"Rob Martin",
"age":45,
"loanType":"PL",
"loanAmount":50000
}'



**Decision Model Check**

curl --location 'http://localhost:8282/engine-rest/decision-definition/key/adult-check/evaluate' \
--header 'Content-Type: application/json' \
--data '{
"variables": {
"inputValue": {
"value": 27,
"type": "integer"
}
}
}'



