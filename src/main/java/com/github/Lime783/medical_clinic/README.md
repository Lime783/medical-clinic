jakie metody Lombok generuje dla buildera pacjenta i jak to sprawdzić bez zaglądania do kodu Lomboka

Trzeba użyc javap -p 'ścieżka/do/klasy/z/builderem\$Builder'  
U mnie javap -p 'target/classes/com/github/Lime783/medical_clinic/patient/Patient$PatientBuilder'

``` java
public class com.github.Lime783.medical_clinic.patient.Patient$PatientBuilder {
private java.lang.String email;
private java.lang.String password;
private java.lang.String idCardNo;
private java.lang.String firstName;
private java.lang.String lastName;
private java.lang.String phoneNumber;
private java.time.LocalDate birthday;
com.github.Lime783.medical_clinic.patient.Patient$PatientBuilder();
public com.github.Lime783.medical_clinic.patient.Patient$PatientBuilder email(java.lang.String);
public com.github.Lime783.medical_clinic.patient.Patient$PatientBuilder password(java.lang.String);
public com.github.Lime783.medical_clinic.patient.Patient$PatientBuilder idCardNo(java.lang.String);
public com.github.Lime783.medical_clinic.patient.Patient$PatientBuilder firstName(java.lang.String);
public com.github.Lime783.medical_clinic.patient.Patient$PatientBuilder lastName(java.lang.String);
public com.github.Lime783.medical_clinic.patient.Patient$PatientBuilder phoneNumber(java.lang.String);
public com.github.Lime783.medical_clinic.patient.Patient$PatientBuilder birthday(java.time.LocalDate);
public com.github.Lime783.medical_clinic.patient.Patient build();
public java.lang.String toString();
}
```

(1) skąd MapStruct wie, co przepisać, gdy nic nie konfigurujesz;

MapStruct w czasie kompilacji pisze kod, który przepisuje pola o tych samych nazwach w modelu i toDto lub odpowiednich
commandów.

(2) po co procesor adnotacji i co dzieje się w czasie kompilacji;

By adnotacje cokolwiek robiły, bo np. Lombok nie działa w trakcie uruchomienia aplikacji, tylko w trakcie kompilacji. \
W trakcie kompilacji kompilator czyta adnotacje w kodzie i dopisuje metody do powstającej klasy

(3) czemu w pomie są dwa wpisy MapStructa, mapstruct i mapstruct-processor;

dependency org.mapstruct:mapstruct daje adnotacje, a mapstruct-processor w annotationProcessorPaths pisze kod.

(4) gdzie lądują wygenerowane mappery i dlaczego tam;

W osobnej klasie by spring boot zrobił z mappera beana do wstrzykiwania

(5) jak skonfigurować mapowanie, gdy domyślne nie pasuje (@Mapping z target, source i kropką w source);

``` java
@Mapping(target = "available",
         source = "stock",
         qualifiedByName = "inStock")
```

target to pole wyniku, source to pole źródła, a ewentualna kropka w source sięga do pola danego obiektu w środku (np.
source = "book.title")

(6) jak policzyć pole, którego źródło nie ma (@Named z qualifiedByName)

``` java
@Named("inStock")
    default boolean inStock(int stock) {
        return stock > 0;
    }
```

W qualifiedByName z pytania 5 wstawić nazwę metody z adnotacji @Named, która policzy brakującą wartość dla danego pola