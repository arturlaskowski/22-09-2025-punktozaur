# punktozaur - Zadanie 1

Punktozaur to aplikacja, która pozwala zbierać punkty lojalnościowe i wymieniać je na kupony rabatowe.

## Zadanie

Twoim zadaniem jest oddzielenie modułu lojalnościowego (loyalty) od modułu kuponów (coupon).
Po wykonaniu zadania powinny istnieć pakiety:
* [pl.punktozaur.coupon](./src/main/java/pl/punktozaur/coupon)
* [pl.punktozaur.loyalty](./src/main/java/pl/punktozaur/loyalty) (został już stworzony)
* [pl.punktozaur.common](./src/main/java/pl/punktozaur/common)

Moduł lojalnościowy nie może korzystać z żadnych elementów modułu kuponów, a moduł kuponów może korzystać tylko z [LoyaltyFacade](./src/main/java/pl/punktozaur/loyalty/LoyaltyFacade.java).

Istnieją już testy architektoniczne, które sprawdzą poprawność implementacji – jeśli wszystko zostanie zaimplementowane prawidłowo, testy będą zielone.
[Testy architektury](./src/test/java/pl/punktozaur/architecture/ArchitectureTest.java)

Oczywiście, po wprowadzeniu tych zmian wszystkie pozostałe testy po Rest API również powinny przejść.
Do weryfikacji relacji między modułami służy test [End to End](./src/test/java/pl/punktozaur/CreateCouponEndToEndTest.java).

Jeśli lubisz manualnie weryfikować zachowanie aplikacji, [kolekcja Postmana](punktozaur_1.postman_collection.json) jest do Twojej dyspozycji.

## Testy akceptacyjne w tym zadaniu

W tym zadaniu **nie musisz** przenosić testów akceptacyjnych do osobnych modułów.  
Normalnie powinno się to zrobić (tak jak jest to zrobione w projekcie *kopytka* i będzie w rozwiązaniu tego zadania).

Głównym celem tego zadania jest jednak **podział na moduły** oraz weryfikacja, czy obserwowalne zachowanie aplikacji się nie zmieniło.  
To jest już sprawdzane przez testy architektury oraz testy e2e.

👉 Jeśli zrobiłeś to zadanie, podniosłeś rękę (online) i czekasz na pozostałych — możesz samodzielnie przenieść testy akceptacyjne do osobnych modułów. 😄

### Powodzenia!
