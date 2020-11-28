#!/bin/bash
curl -XPOST localhost:8080/billing/addUser -d "user=sasha&money=100000"
curl -XPOST localhost:8080/billing/addUser -d "user=sergey&money=100000"
curl -XPOST localhost:8080/billing/addUser -d "user=user1&money=100000"
curl -XPOST localhost:8080/billing/addUser -d "user=user2&money=100000"
curl -XPOST localhost:8080/billing/addUser -d "user=user3&money=100000"
curl -XPOST localhost:8080/billing/addUser -d "user=user4&money=100000"
curl -XPOST localhost:8080/billing/addUser -d "user=user5&money=100000"
curl -XPOST localhost:8080/billing/addUser -d "user=user6&money=100000"
for i in {1..2500}
do
        curl -XPOST localhost:8080/billing/sendMoney -d "from=sasha&to=sergey&money=1" &
        curl -XPOST localhost:8080/billing/sendMoney -d "from=sergey&to=user1&money=1" &
        curl -XPOST localhost:8080/billing/sendMoney -d "from=user1&to=user2&money=1" &
        curl -XPOST localhost:8080/billing/sendMoney -d "from=user2&to=user3&money=1" &
        curl -XPOST localhost:8080/billing/sendMoney -d "from=user3&to=user4&money=1" &
        curl -XPOST localhost:8080/billing/sendMoney -d "from=user4&to=user5&money=1" &
        curl -XPOST localhost:8080/billing/sendMoney -d "from=user5&to=user6&money=1" &
        curl -XPOST localhost:8080/billing/sendMoney -d "from=user6&to=sasha&money=1" &
done
wait
curl localhost:8080/billing/stat
