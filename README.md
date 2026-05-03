# 📋 JobHunter - Nền tảng Quản lý Việc làm

**JobHunter** là một ứng dụng web RESTful được xây dựng với **Spring Boot 3.2.4** và **Java 17**, cung cấp một nền tảng quản lý việc làm toàn diện. Ứng dụng cho phép quản lý công ty, công việc, hồ sơ ứng viên, kỹ năng, vai trò người dùng và hệ thống phân quyền.

## 🎯 Tính năng chính

- ✅ **Quản lý Người dùng**: Đăng ký, đăng nhập, quản lý hồ sơ người dùng
- ✅ **Quản lý Công ty**: Thêm, sửa, xóa, tìm kiếm thông tin công ty
- ✅ **Quản lý Công việc**: Tạo và quản lý các tin tuyển dụng
- ✅ **Quản lý Hồ sơ**: Lưu trữ và quản lý hồ sơ ứng viên
- ✅ **Hệ thống Kỹ năng**: Phân loại và quản lý các kỹ năng
- ✅ **Phân quyền & Vai trò**: Kiểm soát truy cập dựa trên vai trò (Role-based Access Control - RBAC)
- ✅ **Xác thực JWT**: Bảo mật với JWT token
- ✅ **OAuth2 Resource Server**: Hỗ trợ OAuth2
- ✅ **Gửi Email**: Hệ thống gửi email tích hợp
- ✅ **API Documentation**: Tài liệu API tự động với Swagger/OpenAPI
- ✅ **Tải lên File**: Hỗ trợ tải lên file (hồ sơ, hình ảnh, v.v.)

## 🛠️ Công nghệ sử dụng

### Backend
- **Java 17**: Ngôn ngữ lập trình
- **Spring Boot 3.2.4**: Framework chính
- **Spring Data JPA**: ORM và truy vấn cơ sở dữ liệu
- **Spring Security**: Xác thực và phân quyền
- **Spring Mail**: Gửi email
- **Spring Validation**: Kiểm tra dữ liệu đầu vào
- **Thymeleaf**: Template engine

### Database
- **MySQL**: Cơ sở dữ liệu relational

### Thư viện bổ sung
- **Lombok**: Giảm code boilerplate (getters, setters, constructors)
- **SpringDoc OpenAPI**: Tài liệu API tự động (Swagger UI)
- **JPA Spring Filter**: Lọc và tìm kiếm dữ liệu nâng cao
- **Spring Security Thymeleaf Extras**: Tích hợp Security với Thymeleaf

### Build Tool
- **Gradle**: Quản lý build và dependencies

## 📋 Yêu cầu hệ thống

- **Java 17+**
- **MySQL 8.0+**
- **Gradle 7.0+** (hoặc sử dụng gradlew)
- **Git**

## 🚀 Cài đặt và chạy

### 1. Clone dự án
```bash
git clone <repository-url>
cd jobhunter
```

### 2. Cấu hình cơ sở dữ liệu

Tạo cơ sở dữ liệu MySQL:
```sql
CREATE DATABASE jobhunter;
```

Chỉnh sửa file `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jobhunter
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Cấu hình JWT

Tạo secret key JWT trong `application.properties`:
```properties
hoidanit.jwt.base64-secret=your_base64_secret_key
hoidanit.jwt.access-token-validity-in-seconds=8640000
hoidanit.jwt.refresh-token-validity-in-seconds=8640000
```

### 4. Cấu hình Email (Gmail)

Cập nhật thông tin email trong `application.properties`:
```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

> 💡 Sử dụng **App Password** thay vì mật khẩu thường khi dùng Gmail

### 5. Cấu hình tải lên file

Thiết lập đường dẫn tải lên file:
```properties
hoidanit.upload-file.base-uri=file:///path/to/upload/
```

### 6. Chạy ứng dụng

**Sử dụng Gradle:**
```bash
# Windows
gradlew bootRun

# Linux/Mac
./gradlew bootRun
```

Ứng dụng sẽ chạy tại: `http://localhost:8080`

## 📚 API Documentation

Sau khi chạy ứng dụng, truy cập Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

## 📁 Cấu trúc dự án

```
src/main/java/vn/hoidanit/jobhunter/
├── config/                 # Cấu hình ứng dụng (Security, CORS, OpenAPI, v.v.)
├── controller/             # Xử lý các request HTTP
├── domain/                 # Các model/entity (User, Job, Company, Resume, v.v.)
│   ├── request/            # DTO cho request
│   └── response/           # DTO cho response
├── repository/             # Truy vấn cơ sở dữ liệu
├── service/                # Logic nghiệp vụ
├── util/                   # Các tiện ích (annotation, constant, error handling)
│   ├── annotation/         # Custom annotations
│   ├── constant/           # Hằng số
│   └── error/              # Xử lý lỗi
└── JobhunterApplication.java  # Main class
```

## 🔐 Bảo mật

- **JWT Authentication**: Xác thực người dùng sử dụng JWT token
- **Role-Based Access Control (RBAC)**: Kiểm soát quyền truy cập dựa trên vai trò
- **CORS Configuration**: Cấu hình Cross-Origin Resource Sharing
- **Spring Security**: Bảo vệ endpoint API

## 🗄️ Entities chính

| Entity | Mô tả |
|--------|-------|
| **User** | Người dùng hệ thống (ứng viên hoặc nhà tuyển dụng) |
| **Company** | Thông tin công ty tuyển dụng |
| **Job** | Tin tuyển dụng |
| **Resume** | Hồ sơ ứng viên |
| **Skill** | Kỹ năng yêu cầu |
| **Role** | Vai trò người dùng trong hệ thống |
| **Permission** | Quyền truy cập các tính năng |
| **Subscriber** | Người theo dõi tin tuyển dụng |

## 🧪 Testing

Chạy các test:
```bash
gradlew test
```

## 📝 Logging

Ứng dụng sử dụng **SLF4J** với **Logback** mặc định của Spring Boot.

## 🔧 Development

### IDE được khuyến nghị
- **IntelliJ IDEA** (cộng đồng hoặc phiên bản trả phí)
- **VS Code** với Extension Pack for Java

### Cài đặt Lombok
Đảm bảo IDE của bạn có hỗ trợ Lombok:
- **IntelliJ IDEA**: Cài đặt plugin Lombok
- **VS Code**: Cài đặt extension Lombok

## 📦 Build

Tạo JAR file:
```bash
gradlew build
```

File JAR sẽ được tạo tại: `build/libs/jobhunter-0.0.1-SNAPSHOT.jar`

Chạy JAR file:
```bash
java -jar build/libs/jobhunter-0.0.1-SNAPSHOT.jar
```

## 🐳 Docker

Ứng dụng có Dockerfile. Để build Docker image:
```bash
docker build -t jobhunter:latest .
docker run -p 8080:8080 jobhunter:latest
```

## 🤝 Đóng góp

Mọi đóng góp đều được chào đón! Vui lòng:
1. Fork dự án
2. Tạo nhánh tính năng (`git checkout -b feature/AmazingFeature`)
3. Commit thay đổi (`git commit -m 'Add some AmazingFeature'`)
4. Push lên nhánh (`git push origin feature/AmazingFeature`)
5. Mở Pull Request

