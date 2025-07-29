## Zadanie: Wdrożenie CQRS podejścia command-handler z wykorzystaniem wzorca Mediator w domenie **Coupon**

### Cel

Zaimplementuj architektoniczny wzorzec **CQRS** w domenie `Coupon`, wykorzystując wzorzec **Mediator** w taki sposób, aby:
* [CouponController](src/main/java/pl/punktozaur/coupon/web/CouponController.java) tworzył **komendy (command)** i przesyłał je do **Mediatora**,
* **Mediator** przekazywał komendy do odpowiednich **Command Handlerów**.

Przykładową implementację znajdziesz w projekcie `kopytka`, na branchu `cqrs-command-handler`.

---

### Obecny stan

W projekcie istnieje już wstępna implementacja CQRS:
* [CouponService](src/main/java/pl/punktozaur/coupon/application/CouponService.java) odpowiada za operacje **zmieniające stan**,
* [CouponQueryService](src/main/java/pl/punktozaur/coupon/application/CouponQueryService.java) odpowiada za **operacje odczytu**.

Twoim zadaniem jest **przeniesienie logiki modyfikującej stan** z `CouponService` do dedykowanych **Command Handlerów** i zmiany podejścia w kontrolerze.

### Wskazówki
* W projekcie znajdują się już klasy wspierające Mediatora w pakiecie [common.command](src/main/java/pl/punktozaur/common/command).
* Po wprowadzeniu zmian, `CouponService` powinien być **usunięty** z projektu.
* W testach architektury ([ArchitectureTest](src/test/java/pl/punktozaur/architecture/ArchitectureTest.java)) weryfikowana jest reguła, że `CouponController` **nie może zależeć od `CouponService`**.
* Zachowanie aplikacji (request/response) **nie powinno się zmienić** po refaktoryzacji.
* W testach może być konieczne:
    * dostosowanie DTO wykorzystywanych w metodach pomocniczych,
    * zamiana wywołań serwisów na wywołania handlerów.

**Powodzenia!**

