## Zadanie 5

Twoim zadaniem jest przesłanie wiadomości za pośrednictwem Kafki z **coupon service** do **loyalty service** podczas tworzenia kuponu, w celu odjęcia punktów.

Wiadomość powinna być wysłana na topic `loyalty-commands` (topic ten jest automatycznie tworzony w [docker-compose](infrastructure/docker-compose.yml)).

### Kroki do wykonania

1. **Zdefiniuj strukturę wiadomości (schemat Avro)**
   Umieść schemat w katalogu [src/main/resources/avro](common/src/main/resources/avro).

2. **Wygeneruj klasy Java**
   Po zdefiniowaniu schematu Avro, w module `common` wykonaj polecenie  `mvn compile` w module common.
   Spowoduje to automatyczne wygenerowanie klas Java, które mogą być używane zarówno przez producenta, jak i odbiorcę wiadomości.

3. **Wyślij wiadomość podczas tworzenia kuponu**
   Zaimplementuj wysyłanie wiadomości na topic `loyalty-commands` podczas tworzenia kuponu.

4. **Zaktualizuj test akceptacyjny**
   Zmodyfikuj test [CreateCouponAcceptanceTest](coupon-service/src/test/java/pl/punktozaur/coupon/acceptance/CreateCouponAcceptanceTest.java), aby weryfikował, czy wiadomość jest wysłana na Kafkę podczas tworzenia kuponu.
   Test zamiast `BaseIntegrationTest` powinien rozszerzać `KafkaIntegrationTest`, przykład w klasie [CustomerAcceptanceTest](customer-service/src/test/java/pl/punktozaur/customer/acceptance/CustomerAcceptanceTest.java).

5. **Odbierz wiadomość w loyalty service**
   W **loyalty service** zaimplementuj listener, który odbierze wiadomość z topicu `loyalty-commands` i odejmie punkty.

6. **Przetestuj swoje rozwiązanie**
   Uruchom wszystkie mikroserwisy i wykonaj test [Zad5EndToEndTest](coupon-service/src/test/java/pl/punktozaur/coupon/Zad5EndToEndTest.java).
   Jeśli test będzie zielony, oznacza to, że zadanie zostało wykonane poprawnie.

   > Tego typu testów zazwyczaj nie pisze się w normalnych projektach – ten został przygotowany wyłącznie w celu weryfikacji poprawności rozwiązania.

   Jeśli chcesz samodzielnie sprawdzić działanie aplikacji przez API, możesz skorzystać z [kolekcji Postmana](punktozaur-2.postman_collection.json).

---

### Wskazówki

* **Zatrzymaj niepotrzebne kontenery Dockera z projektu Kopytka**
  W katalogu Kopytka znajduje się skrypt `docker-clean.sh` – taki sam jak tutaj :)

* **Uruchom infrastrukturę** przy pomocy [docker-compose](infrastructure/docker-compose.yml).

* **GUI do Kafki**
  Po uruchomieniu, pod adresem [http://localhost:8080/](http://localhost:8080/) dostępne jest GUI do Kafki z podłączonym Schema Registry. Możesz tam sprawdzić, jakie wiadomości pojawiły się na danym topicu.

### Baza danych

Każda baza danych to osobny schemat, co zapewnia separację oraz oszczędność zasobów lokalnie, a w środowisku produkcyjnym umożliwia korzystanie z osobnych baz danych.
Po zalogowaniu się do bazy jako użytkownik `admin_user` z hasłem `admin_password` (`jdbc:postgresql://localhost:5432/punktozaurdb`), masz dostęp do wszystkich schematów.

### pgAdmin (opcjonalnie)

Jeśli nie korzystasz z IntelliJ w wersji Ultimate, możesz użyć **pgAdmina** do zarządzania bazą danych.
Aby go uruchomić, skorzystaj z pliku [docker-compose.pgadmin.yml](infrastructure/docker-compose.pgadmin.yml).

Po około minucie pgAdmin będzie dostępny pod adresem:
[http://localhost:5050](http://localhost:5050)

Baza danych **punktozaurDb** powinna być już skonfigurowana.
Jeśli pojawi się okno z prośbą o hasło lub dane logowania, wpisz `postgres`.

### Czyszczenie danych
  Jeśli chcesz wyczyścić dane z bazy, usunąć topiki z Kafki i zatrzymać wszystkie kontenery Dockera, użyj skryptu [docker-clean.sh](infrastructure/docker-clean.sh).
