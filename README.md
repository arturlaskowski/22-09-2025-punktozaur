# Rozwiązanie 
## Zadanie: Wdrożenie podejścia CQRS z command-handler przy użyciu wzorca Mediator w module **Coupon**

### Cel

Zaimplementuj architektoniczny wzorzec **CQRS** w module `Coupon` z wykorzystaniem wzorca **Mediator** w taki sposób, aby:
* [CouponController](src/main/java/pl/punktozaur/coupon/web/CouponController.java) tworzył **komendy (command)** i przesyłał je do **Mediatora**,
* **Mediator** przekazywał komendy do odpowiednich **Command Handlerów**.

Przykładową implementację znajdziesz w projekcie `kopytka`, na branchu `cqrs-command-handler`.

---

### Obecny stan

W projekcie istnieje już wstępna implementacja CQRS:
* [CouponService](src/main/java/pl/punktozaur/coupon/application/CouponService.java) odpowiada za operacje **zmieniające stan**,
* [CouponQueryService](src/main/java/pl/punktozaur/coupon/application/CouponQueryService.java) odpowiada za **operacje odczytu**.

Twoim zadaniem jest **przeniesienie logiki modyfikującej stan** z `CouponService` do dedykowanych **Command Handlerów** oraz zmiana podejścia w kontrolerze.

### Wskazówki
* W projekcie znajdują się już klasy wspierające Mediatora w pakiecie [common.command](src/main/java/pl/punktozaur/common/command).
* Po wprowadzeniu zmian `CouponService` powinien zostać **usunięty** z projektu.
* W testach architektury ([ArchitectureTest](src/test/java/pl/punktozaur/architecture/ArchitectureTest.java)) weryfikowana jest reguła, że `CouponController` **nie może zależeć od `CouponService`**.
* Zachowanie aplikacji (request/response) **nie powinno się zmienić** po refaktoryzacji. Test [end to end](src/test/java/pl/punktozaur/CreateCouponEndToEndTest.java) sprawdza to automatycznie.
* Testy akceptacyjne zostały tymczasowo zakomentowane. Jeśli przejdzie test [end to end](src/test/java/pl/punktozaur/CreateCouponEndToEndTest.java) oraz [ArchitectureTest](src/test/java/pl/punktozaur/architecture/ArchitectureTest.java), zadanie można uznać za zakończone (ręka do góry - online).
* Jeśli inni jeszcze pracują a tobie zostało czasu warto również dostosować testy akceptacyjne (oczywiście w rozwiązaniu będą dostosowane) :).

**Powodzenia!**
