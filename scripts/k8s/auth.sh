#!/bin/bash

user=$1
pass=$2
host="https://biblioteca-ada.com:30821/actuator"

if [[ -z "$user" || -z "$pass" ]]; then
    echo "Uso: ./auth.sh <usuario> <senha>"
    exit 1
fi

echo "Testando o Auth do Actuator"
echo "Url: $host"

echo -e "\nSem credenciais (esperado: 401)..."
status=$(curl -sk -o /dev/null -w "%{http_code}" "$host")
echo "HTTP Code => $status"

echo -e "\nCom credenciais (esperado: 200)..."
status=$(curl -sk -o /dev/null -w "%{http_code}" -u "$user:$pass" "$host")
echo "HTTP Code => $status"
