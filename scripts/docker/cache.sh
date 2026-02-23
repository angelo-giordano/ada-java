#!/bin/bash

host="https://localhost/api/catalog/books"
echo "Testando o Cache"
echo "Url: $host"

echo -e "\nGET Sem Auth (esperado: MISS na primeira, HIT na segunda)..."
curl -skI "$host" | grep -E "X-Cache-Status|HTTP/"
curl -skI "$host" | grep -E "X-Cache-Status|HTTP/"

echo -e "\nGET Com Auth (esperado: BYPASS)..."
curl -skI -H "Authorization: Bearer token" "$host" | grep -E "X-Cache-Status|HTTP/"

echo -e "\nPOST (não cacheado)..."
curl -s -D - -o /dev/null -X POST "$host" \
  -H "Content-Type: application/json" \
  -d '{"isbn": "9780134685991", "title": "Effective Java", "authorName": "Joshua Bloch", "category": "Programming", "totalQuantity": 3}' \
  | grep -E "X-Cache-Status|HTTP/"
