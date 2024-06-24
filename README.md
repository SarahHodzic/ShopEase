## DOKUMENTACIJA

### Opis rada aplikacije

Aplikacija ShopEase je moderna Android aplikacija razvijena za pregled i kupovinu različitih proizvoda putem integracije s Web API-jem. Cilj aplikacije je pružiti korisnicima intuitivno iskustvo pri pregledu i kupovini proizvoda iz različitih kategorija.

### Početni ekran (Landing page)

Na početnom ekranu korisnici mogu pregledati različite kategorije proizvoda poput elektronike, nakita, odjeće za muškarce i žene. Svaka kategorija prikazana je kao kartica s odgovarajućom slikom i nazivom.

#### Elementi početnog ekrana:

1. **Top traka (TopAppBar):**
   - Prikazuje naziv aplikacije i opciju za navigaciju unatrag (back) ili otvaranje košarice.
   - Omogućuje navigaciju na druge dijelove aplikacije, poput ekrana za kupovinu ili povratak na početni ekran.

2. **Kategorije proizvoda:**
   - Prikazuju se kao kartice (CategoryCard) koje sadrže sliku i naziv kategorije proizvoda.
   - Korisnik može kliknuti na svaku karticu kako bi vidio proizvode u toj kategoriji.

3. **Klikabilne kartice (CategoryCard):**
   - Svaka kartica sadrži sliku koja predstavlja kategoriju proizvoda.
   - Ispod slike nalazi se naziv kategorije.
   - Korisnik može kliknuti na karticu kako bi prikazao proizvode u toj kategoriji.

4. **Optimizacija za različite orijentacije:**
   - Aplikacija je prilagođena za vertikalni i horizontalni prikaz ekrana.
   - Prikaz kategorija proizvoda je optimiziran za najbolje iskustvo korisnika, bez obzira na orijentaciju ekrana.

![image](https://github.com/SarahHodzic/ShopEase/assets/82335709/970961e7-203b-402b-9f81-a0948355f678)


### Ekran sa artiklima (CategoryScreen)

"CategoryScreen" je ključni ekran aplikacije ShopEase koji korisnicima omogućuje pregled proizvoda u odabranim kategorijama. Sadrži top traku s nazivom kategorije i navigacijskim dugmićima te prikazuje dinamički sadržaj ovisno o stanju: prikazuje sliku tokom učitavanja, poruku o grešci u slučaju problema ili listu artikala (proizvoda) u karticama za uspješno dohvaćene podatke. Svaka kartica artikla omogućuje klik za detaljnije informacije, čineći pregled proizvoda intuitivnim i prilagođenim različitim korisničkim iskustvima.

#### Elementi ekrana sa artiklima:

1. **Top traka (TopAppBar):**
   - Prikazuje naziv kategorije proizvoda kao naslov.
   - Sadrži dugmić za povratak na prethodni ekran (ako je moguće) i gumb za otvaranje košarice.

2. **Glavni sadržaj:**
   - Ovisno o stanju (Loading, Success ili Error), prikazuje se odgovarajući ekran:
     - LoadingScreen: Prikazuje se slika koja označava da se učitavaju podaci.
     - ErrorScreen: Prikazuje se poruka o grešci i ikona za grešku. Omogućuje ponovno pokušavanje.
     - ResultScreen: Prikazuje listu artikala (proizvoda) u obliku kartica.

3. **Kartice artikala (ArticleItem):**
   - Svaki artikal (proizvod) prikazan je unutar kartice.
   - Kartica ima sliku artikla, naslov, kategoriju i cijenu.
   - Omogućuje klik na artikal kako bi se otvorili detalji tog artikla.

4. **Optimizacija za različita stanja:**
   - Ekran je optimiziran za prikaz tokom učitavanja (Loading), uspješnog dohvaćanja podataka (Success) ili prikazivanja greške (Error).
   - Prilagođava se kako bi korisnicima omogućio što jednostavnije i intuitivnije iskustvo pregledavanja proizvoda.
  
All products kategorija:
  ![image](https://github.com/SarahHodzic/ShopEase/assets/82335709/440ca03a-3a20-41ac-8965-e6182c6eac92)
Jewelery kategorija:
![image](https://github.com/SarahHodzic/ShopEase/assets/82335709/8f09da3d-9582-4789-846e-00b7904fdbdd)

Aplikacija posjeduje pejzažni i portretni prikaz ekrana
Kada je korisnik u portret mode-u svi sve kartice sa kategorijama artikala su poredane u jednoj koloni jedni ispod drugih (LazyColumn). S druge strane kada je korisnik u pejzažnom mode-u kategorije proizvoda su poredane u dvije kolone (LazyVerticalgrid) 
![image](https://github.com/SarahHodzic/ShopEase/assets/82335709/5e2fea37-704f-41c5-97bc-b53d29f5a60f)

Ovi načini rada prilagođavaju se orijentaciji uređaja i omogućuju korisnicima optimalan prikaz kategorija proizvoda ovisno o veličini ekrana i korisničkom iskustvu.

### Ekran sa košaricom

Ekran sa košaricom je ekran na kojem kupci mogu vidjeti šta su do sada odabrali kupiti. Sadrži dva dugmeta za sortiranje elemenata po cijeni, tekst u kojem je prikazana ukupna cijena artikala u košarici, dugme za kupovinu i prikaz svih artikala u vidu Card komponenata. Ukoliko se kupac predomisli i više ne želi kupiti određeni artikal, može pritisnuti na ikonu brisanja koja se nalazi na svakoj Card komponenti i time će se artikal ukloniti iz košarice. Kada je kupac zadovoljan artiklima, može kliknuti na dugme Kupi (Buy), nakon čega će se pojaviti AlertDialog s prikazom ukupne cijene, dugme za dijeljenje artikala koje kupac želi kupiti, kao i Cancel i Buy dugmad za odustajanje od kupovine ili potvrdu kupovine. Kada se klikne Buy dugme, svi artikli se brišu iz baze podataka i vraća se na početni ekran.

![image](https://github.com/SarahHodzic/ShopEase/assets/82335709/be8ccf50-5455-42fc-980c-990aa2206469)
![image](https://github.com/SarahHodzic/ShopEase/assets/82335709/7507177d-d967-4117-b623-9bea2c63b26d)


### Ekran sa detaljima artikla

Ovaj ekran predstavlja "ArticleDetailsScreen" i prikazuje detalje o pojedinačnom artiklu. Korisnici mogu vidjeti sliku, naslov, cijenu, opis, kategoriju i ocjene artikla. Pored toga, omogućava dodavanje artikla u korpu putem plutajućeg dugmeta, a na dnu ekrana se pojavljuje prilagođena Snackbar obavijest koja potvrđuje da je artikal dodan u korpu.

#### Elementi ekrana i njihov opis:

1. **Plutajuće dugme (FloatingActionButton):**
   - Dugme za dodavanje artikla u korpu.
   - Prikazuje Snackbar obavijest kada je artikal dodan u korpu.

2. **SnackbarHost:**
   - Prilagođena Snackbar obavijest koja se prikazuje kada je artikal dodan u korpu.
   - Sadrži ikonu potvrde (zelena kvačica) i poruku.

3. **Slika artikla (AsyncImage):**
   - Prikazuje sliku artikla.
   - Postavljena je da zauzima 300 dp visine i cijelu širinu ekrana.
   - Sadrži efekte prelaska (crossfade).

4. **Prilagođena Snackbar (CustomSnackbar):**
   - Prikazuje prilagođenu Snackbar obavijest.

### Opis arhitekture aplikacije

Korištena je MVVM (Model-View-ViewModel) arhitektura i Jetpack Compose biblioteke za izradu korisničkog interfejsa.

#### Slojevi arhitekture:

1. **View (Pogled):**
   - View sloj sadrži sve UI komponente koje su izgrađene koristeći Jetpack Compose.
   - Ovaj sloj je odgovoran za prikazivanje podataka i prikupljanje korisničkih interakcija.

2. **ViewModel:**
   - ViewModel sloj je odgovoran za upravljanje logikom aplikacije i poslovnim podacima.
   - ViewModel komunicira sa Model slojem za dohvaćanje podataka i sa
