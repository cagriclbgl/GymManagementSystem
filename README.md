# cagri-ekrem_gymSystem
Gym Management System with Java
# Spor Salonu Yönetim Sistemi API'si

## Projenin Amacı

Bu proje, spor salonlarının yönetimini kolaylaştırmak amacıyla geliştirilmiştir. Sistem, kullanıcıların kayıt olmalarını, giriş yapmalarını, derslere katılmalarını ve eğitmen seçimlerini kolaylaştırmayı hedefler. Aynı zamanda yönetim için ders ve eğitmen bilgilerini düzenlemeye olanak tanır.

## Çalışma Prensibi

Sistem, kullanıcı ve eğitmen bilgilerini bir veritabanında saklar ve bu bilgilere erişimi çeşitli endpointler üzerinden sağlar. Aşağıdaki işlemleri gerçekleştirebilirsiniz:
- **Kullanıcı İşlemleri**: Kayıt olma, giriş yapma ve eğitmen seçme, ders seçimi yapma.
- **Eğitmen Yönetimi**: Eğitmenlerin listelenmesi.
- **Ders Seçimi**:Kullanıcıların grup ders seçimi yapabilir.
- **Kullanıcı Silme**:Admin panelinden kullanıcı silinebilir


API, bu işlemleri gerçekleştirmek için temel kontrollere sahiptir:  
- `AuthController`: Kullanıcı kayıt ve giriş işlemleri.
- `CourseController`: Derslerin yönetimi.
- `TrainerController`: Eğitmenlerin listelenmesi.
- `UserController`: Kullanıcı arama.

---

## Kullanılan Teknolojiler

- **Java 17**: Projenin ana programlama dili.
- **Spring Boot**: Hızlı geliştirme için kullanılan web framework.
- **Spring Data JPA**: Veritabanı işlemlerinin kolaylaştırılması için.
- **Mock Test**: Test ortamında kullanılan yerel veritabanı.
- **Maven**: Proje yapılandırması ve bağımlılık yönetimi.
- **MySQL**: Veritabanı olarak kullanıldı.
- **Hibernate**:Veri tabanı entegresyano ve tablo oluşturulması.

---

## Kurulum ve Çalıştırma

### Gereksinimler
** Java 17 yüklü olmalıdır. **
** Maven yüklü olmalıdır. **
** MySQL yüklü olmalıdır. **

### Kurulum Adımları
1. **Projenin Klonlanması**:
   ```bash
   git clone <proje-reposu-url>
   cd <>

2. **Veritabanını Ayarlayın**
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/gym_db
   spring.datasource.username=admin
   spring.datasource.password=123456
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

4. **Bağımlılıkları yükleyin**
    Terminale 'mvn clean install' yazın
   
5. **Uygulamayı başlatın**
    Terminale 'mvn spring-boot:run'

 ### Kullanım 
 1.Login Page: Üye ve admin girişini sağlar.
 2.Register Page: Üye kayıt işlemi yapar.
 3.Home Page: Course- Trainer seçimi- Beslenme- Üyelik- Account kısımları vardır.
 4.Admin Page: Admin kullanıcısı user silebilir.

 

