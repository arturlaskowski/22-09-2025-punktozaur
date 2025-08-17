# punktozaur - Zadanie 2.1

Zaimplementuj **testy kontraktowe** dla komunikacji przez REST API.

## Przygotowanie

W plikach `pom.xml` zostały już dodane zależności i konfiguracja dla testów kontraktowych, więc nie musisz tam nic zmieniać.

Klasa definiująca, jak aplikacja ma odpowiadać na żądania opisane w kontraktach – [ContractTestBase](./loyalty-service/src/test/java/pl/punktozaur/loyalty/contracts/ContractTestBase.java) – również została już zaimplementowana.

Twoje zadanie to napisanie kontraktów i weryfikacja, czy działają poprawnie.

## Kontrakty

`loyalty-service` wystawia API, z którego korzystają `coupon-service` i `customer-service`.

Kontrakty (w formacie `.groovy`) należy umieścić w katalogu:

```
loyalty-service/src/test/resources/contracts
```

Jest to domyślna ścieżka używana przez framework.

Na podstawie kontraktów w `loyalty-service`:

* automatycznie generowane są testy po stronie providera,
* tworzony jest plik `.stubs.jar`, który trafia do katalogu `target`.

Konsumenci (`coupon-service` i `customer-service`) definiują testy integracyjne, które korzystają z wygenerowanych stubów (`.stubs.jar`) z `loyalty-service`. Dzięki temu mogą upewnić się, że ich implementacja klienta jest zgodna z wymaganiami opisanymi w kontrakcie.

## Uwagi techniczne

Jeśli korzystasz z adnotacji `@AutoConfigureStubRunner`, podając nazwę `stubs`, to odwołujesz się do pliku JAR z innego mikroserwisu – on **musi istnieć**.

Wcześniej, w mikroserwisie, który ma ten plik utworzyć, możesz wykonać komendę: `mvn install`

## Weryfikacja

Po zaimplementowaniu kontraktów sprawdź ich działanie, np.:

* zmień nazwę pola u dostawcy → testy powinny się wyłożyć,
* zmień nazwę pola u odbiorcy → testy również powinny się wyłożyć.

## Podsumowanie

a) **Kontrakt dla `customer-service`** – tworzenie konta lojalnościowego w `loyalty-service/src/test/resources/contracts`.

* Zaimplementuj testy integracyjne w `customer-service`, które będą korzystały z automatycznie wygenerowanego stub JAR-a z `loyalty-service`.

b) **Kontrakt dla `coupon-service`** – odejmowanie punktów w `loyalty-service/src/test/resources/contracts`.

* Zaimplementuj testy integracyjne w `coupon-service`, które będą korzystały z automatycznie wygenerowanego stub JAR-a z `loyalty-service`.

## Powodzenia! 🚀