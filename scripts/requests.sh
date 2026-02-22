#!/bin/bash

host="https://localhost/api"

echo "Colocando Alguma carga pra ver"

i=0
while true; do
    rand=$RANDOM
    user_body="{\"name\":\"User $rand\",\"email\":\"user$rand@email.com\",\"cpf\":\"$(printf '%011d' $rand)\",\"userType\":\"STUDENT\"}"
    book_body="{\"isbn\":\"978$(printf '%010d' $rand)\",\"title\":\"Book $rand\",\"authorName\":\"Author $rand\",\"category\":\"Programming\",\"totalQuantity\":3}"
    loan_body="{\"userId\":\"user-001\",\"bookId\":\"book-001\"}"

    endpoints=(
        "GET /catalog/books"
        "GET /catalog/books/book-001"
        "GET /catalog/books/search?q=clean"
        "GET /users"
        "GET /users/user-001"
        "GET /lending/loans/user/user-001"
        "POST /catalog/books"
        "POST /users"
        "POST /lending/loans"
    )

    entry="${endpoints[$((i % ${#endpoints[@]}))]}"
    method=$(echo "$entry" | cut -d' ' -f1)
    endpoint=$(echo "$entry" | cut -d' ' -f2)

    if [ "$method" = "POST" ]; then
        case "$endpoint" in
            "/catalog/books") body=$book_body ;;
            "/users") body=$user_body ;;
            "/lending/loans") body=$loan_body ;;
        esac
        status=$(curl -sk -o /dev/null -w "%{http_code}" -X POST \
            -H "Content-Type: application/json" \
            -d "$body" "$host$endpoint")
    else
        status=$(curl -sk -o /dev/null -w "%{http_code}" -X "$method" "$host$endpoint")
    fi

    echo "$i: [$status] $method $host$endpoint"
    i=$((i + 1))
    sleep 1
done
