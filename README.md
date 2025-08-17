## Zadanie 4

Wykorzystaj mechanizm messagingu wbudowany w Spring (`ApplicationEventPublisher`).  
Jest to messaging, który domyślnie opiera się na pamięci aplikacji i działa synchronicznie.

Zadbaj, aby wszystkie moduły były luźno powiązane (nie komunikowały się bezpośrednio ze sobą).  
Zostanie to zweryfikowane przez [Testy Architektoniczne](src/test/java/pl/punktozaur/architecture/ArchitectureTest.java).

Zastąp obecną implementację messagingiem.  
Klasy `...Facade` powinny zostać usunięte.

Po utworzeniu klienta (Customer) nadal powinno być tworzone konto lojalnościowe,  
a przy tworzeniu kuponu powinny być odejmowane punkty z konta lojalnościowego.  
Obserwowalne zachowanie aplikacji nie powinno ulec zmianie.

Zostanie to zweryfikowane przez [Testy End-to-End](src/test/java/pl/punktozaur/CreateCouponEndToEndTest.java).

W konfiguracji testów akceptacyjnych, zamiast stubowania fasady, zastąp ją stubem dla `ApplicationEventPublisher`, np.:

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