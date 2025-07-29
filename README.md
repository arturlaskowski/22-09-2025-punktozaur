## Zadanie 6 

# Zaimplementuj wzorzec Saga dla procesu wydawania kuponu

## Happy Path

1. Podczas tworzenia kuponu Coupon Service wysyła komendę do odjęcia punktów.
2. Po wysłaniu tej komendy tworzony jest kupon w statusie `PENDING`.
3. Serwis lojalnościowy (Loyalty Service) odbiera tę komendę i ją przetwarza.
4. Po pomyślnym odjęciu punktów, Loyalty Service wysyła event z informacją, że udało się odjąć punkty.
5. Na ten event nasłuchuje serwis kuponów (Coupon Service) i zmienia status kuponu na `ACTIVE`.  
   *(Od tej pory kupon może być używany.)*

## Kompensacja (nie udaje się odjąć punktów)

1. Podczas tworzenia kuponu Coupon Service wysyła komendę do odjęcia punktów.
2. Po wysłaniu tej komendy tworzony jest kupon w statusie `PENDING`.
3. Serwis lojalnościowy (Loyalty Service) odbiera tę komendę i ją przetwarza, ale nie udaje się odjąć punktów.
4. Loyalty Service wysyła event z informacją, że nie udało się odjąć punktów.
5. Na ten event nasłuchuje serwis kuponów (Coupon Service) i zmienia status kuponu na `REJECTED`.  
   *(Kupon nie będzie mógł być używany, ale powinna być dostępna informacja o przyczynie odrzucenia)*


Topics potrzebne do wykonania tego zadania zostały już dodane do [docker-compose](infrastructure/docker-compose.yml).
Schematy avro zostały już stworzone [schematy avro](common/src/main/resources/avro).
Encja [Coupon](coupon-service/src/main/java/pl/punktozaur/coupon/domain/Coupon.java) została już dostosowana i zawiera metody oraz statusy, które będą potrzebne do wykonania tego zadania.

Możliwość dodawania konta lojalnościowego przez REST API została usunięta, teraz jest to proces wewnętrzny podczas tworzenia customera.
Jednak odejmowanie punktów w lojalności może odbywać się także z innych powodów niż wydawanie kuponu, więc endpoint do tego zostaje (pamiętaj o tym, projektując sagę).

Aby sprawdzić poprawność swojego Sagi, przy uruchomionych wszystkich mikroserwisach uruchom test:
[Saga Pattern Test](coupon-service/src/test/java/pl/punktozaur/coupon/SagaEndToEndTest.java).

Powodzenia! 
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