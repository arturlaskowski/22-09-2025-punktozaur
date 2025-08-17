## Zadanie 4

Wykorzystaj mechanizm messagingu wbudowany w Spring (`ApplicationEventPublisher`).
Jest to messaging, który domyślnie działa w pamięci aplikacji i działa synchronicznie.

Zadbaj o to, aby wszystkie moduły były luźno powiązane (nie komunikowały się bezpośrednio ze sobą).
Poprawność zostanie zweryfikowana przez [Testy Architektoniczne](src/test/java/pl/punktozaur/architecture/ArchitectureTest.java).

Zastąp obecną implementację messagingiem.
Klasy `...Facade` powinny zostać usunięte.

### Zakres zadania:

1. **Tworzenie Customera:**
    * Po utworzeniu klienta w module `customer` powinien być emitowany event `CustomerCreatedEvent`.
    * Moduł `loyalty` powinien nasłuchiwać tego eventu i tworzyć konto lojalnościowe dla klienta.

2. **Tworzenie kuponu:**
    * Podczas tworzenia kuponu w module `loyalty` powinna być emitowana wiadomość `SubtractPointsCommand` (czemu to się tak nazywa to zaraz sobie powiemy).
    * Moduł `loyalty` powinien nasłuchiwać tego komunikatu i odejmować punkty z konta lojalnościowego.

> Obserwowalne zachowanie aplikacji nie powinno ulec zmianie.
> Poprawność zostanie zweryfikowana przez [Testy End-to-End](src/test/java/pl/punktozaur/CreateCouponEndToEndTest.java).

### Konfiguracja testów akceptacyjnych

Zamiast stubowania fasady, użyj stubu dla `ApplicationEventPublisher`. Przykład:

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
