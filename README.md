## Zadanie 4

Wykorzystaj mechanizm messagingu wbudowany w Spring (`ApplicationEventPublisher`).
Jest to mechanizm komunikacji działający domyślnie w pamięci aplikacji, w sposób synchroniczny.

Zadbaj o to, aby wszystkie moduły były **luźno powiązane** (nie komunikowały się bezpośrednio ze sobą).
Poprawność rozwiązania zostanie zweryfikowana przez [Testy Architektoniczne](src/test/java/pl/punktozaur/architecture/ArchitectureTest.java).

Należy zastąpić obecną implementację messagingiem.
Klasy `...Facade` powinny zostać usunięte.

---

### Zakres zadania

1. **Tworzenie klienta (`Customer`)**

    * Po utworzeniu klienta w module `customer` powinien być emitowany event `CustomerCreatedEvent`.
    * Moduł `loyalty` powinien nasłuchiwać tego eventu i tworzyć konto lojalnościowe dla klienta.
    * Zadanie będzie uznane za ukończone, jeśli:

        * [Testy End-to-End](src/test/java/pl/punktozaur/CreateCouponEndToEndTest.java) będzie zielone,
        * test `checkCustomerLoyaltyModuleDependencies` w [Testach Architektonicznych](src/test/java/pl/punktozaur/architecture/ArchitectureTest.java) będzie zielone.

2. **Tworzenie kuponu (`Coupon`)**

    * Podczas tworzenia kuponu w module `coupon` powinna być emitowana wiadomość `SubtractPointsCommand` (nazewnictwo zostanie wyjaśnione później).
    * Moduł `loyalty` powinien nasłuchiwać tego komunikatu i odejmować punkty z konta lojalnościowego.
    * Zadanie będzie uznane za ukończone, jeśli:

        * [Testy End-to-End](src/test/java/pl/punktozaur/CreateCouponEndToEndTest.java) będzie zielone,
        * test `checkAllModuleDependencies` w [Testach Architektonicznych](src/test/java/pl/punktozaur/architecture/ArchitectureTest.java) będzie zielone.

---

### Konfiguracja testów akceptacyjnych

Dostosowanie testów akceptacyjnych nie jest kluczowe w tym zadaniu — możesz je nawet zakomentować.
(W rozwiązaniu docelowym będą oczywiście poprawione).

Jeśli jednak chcesz je dostosować, wystarczy że:

* zamiast stubowania fasady,
* użyjesz stubu dla `ApplicationEventPublisher`.

Przykład:

```java
@TestConfiguration
class CustomerTestConfig {

    @Bean
    @Primary
    public ApplicationEventPublisher applicationEventPublisher() {
        ApplicationEventPublisher publisher = Mockito.mock(ApplicationEventPublisher.class);
        doNothing().when(publisher).publishEvent(CustomerCreatedEvent.class);
        return publisher;
    }
}
```
