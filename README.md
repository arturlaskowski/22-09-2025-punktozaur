# punktozaur - Zadanie 2.1

Zaimplementuj testy kontraktowe dla komunikacji po REST API.

## Przygotowanie

W plikach `pom.xml` zostały już dodane zależności i konfiguracja dla testów kontraktowych, więc nie musisz nic tam zmieniać.

## Implementacja

Na start zaimplementuj [ContractTestBase](./loyalty-service/src/test/java/pl/punktozaur/loyalty/contracts/ContractTestBase.java).
Definiuje jak aplikacja ma odpowiadać na żądania opisane w kontraktach.

Następnie utwórz kontrakty.

## Kontrakty
`loyalty-service` wystawia API, z którego korzystają `coupon-service` i `customer-service`.
Kontrakty (np. w formacie `.groovy`) będą znajdować się w `loyalty-service`.

Na podstawie kontraktów, w `loyalty-service` generowany są automatycznie testy po stornie providera i plik `.stubs.jar`, który trafia do katalogu `target`.
Konsumenci, czyli `coupon-service` i `customer-service`, definiują testy integracyjne, które korzystają z wygenerowanych stubów (`.stubs.jar`) z `loyalty-service`.
Dzięki temu mogą upewnić się, że ich implementacja klienta jest zgodna z wymaganiami opisanymi w kontrakcie.

## Uwagi techniczne

Jeśli robisz `@AutoConfigureStubRunner` podając nazwę `stubs` to odwołujesz się do jar z innego mikroserwisu, on musi istnieć.
Wcześniej w mikroserwisie, który ma go stworzyć, możesz użyć komendy `maven install`.


## Weryfikacja

Po implementacji kontraktów zweryfikuj ich działanie:
- Zmieniając nazwę jakiegoś pola u dostawcy - test powinien się wysypać
- Zmieniając nazwę pola u odbiorcy - test powinien się wysypać

### Powodzenia!