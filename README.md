# punktozaur – Zadanie 2

Twoim zadaniem jest zaimplementowanie komunikacji synchronicznej między usługami z wykorzystaniem **Feign Client**.

## Wymagania

- Podczas tworzenia klienta w **customer-service** (endpoint `POST /customers`) powinno zostać utworzone konto lojalnościowe w **loyalty-service** (endpoint `POST /loyalty-accounts`).

- Podczas tworzenia kuponu w **coupon-service** (endpoint `POST /coupons`) powinny zostać odjęte punkty z konta lojalnościowego w **loyalty-service** (endpoint `POST /loyalty-accounts/{id}/subtract-points`). Jeśli konto nie istnieje albo brakuje punktów – kupon nie może zostać utworzony.

## Dodatkowe informacje

- Wszystkie zależności są już dodane, więc nie trzeba nic zmieniać w pliku `pom.xml`.
- Po implementacji pamiętaj o dostosowaniu testów. W testach znajdują się klasy `BaseAcceptanceTest`.  
  Wystarczy w nich dodać mock dla wywołania zewnętrznej usługi, np. w customer-service w [BaseAcceptanceTest](customer-service/src/test/java/pl/punktozaur/customer/acceptance/BaseAcceptanceTest.java):

  ```java
  @MockitoBean
  protected LoyaltyServiceClient loyaltyServiceClient;
  ```

## Weryfikacja rozwiązania

1. Uruchom wszystkie mikroserwisy.
2. Poczekaj ok. 30 sekund (na rejestrację w Eurece).
3. Uruchom test [CreateCouponEndToEndTest](./coupon-service/src/test/java/pl/punktozaur/coupon/CreateCouponEndToEndTest.java).

> **Uwaga:** W ten sposób nie testuje się relacji między usługami – ten test służy jedynie do sprawdzenia, czy udało się rozwiązać zadanie.

## Przydatne narzędzie

Do dyspozycji masz kolekcję **Postman**, która pozwoli Ci zweryfikować różne scenariusze działania (opcjonalne): [Postman Collection](punktozaur-2.postman_collection.json)

---

### Powodzenia!