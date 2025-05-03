# RailwayStationAutoTest-Group1

## Giới thiệu

Đây là dự án kiểm thử tự động cho hệ thống đặt vé tàu điện Railway, sử dụng **Selenium WebDriver** và **TestNG**. Dự án áp dụng mô hình **Page Object Model (POM)** giúp mã kiểm thử dễ bảo trì, mở rộng và tái sử dụng.

## Cấu trúc dự án

```
RailwayStationAutoTest-Group1/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── PageObjects/      # Các class đại diện cho từng trang web (POM)
│   │       ├── Common/           # Các class tiện ích, hằng số, cấu hình chung
│   │       └── Testcase/         # Có thể chứa các class test runner hoặc test logic
│   └── test/
│       └── java/
│           └── Railway/          # Các class kiểm thử (Test Cases)
├── pom.xml                       # File cấu hình Maven, khai báo dependencies
└── README.md                     # Tài liệu hướng dẫn (file này)
```

## Công nghệ sử dụng

- **Java 21**
- **Selenium WebDriver 4.20.0**
- **TestNG 7.10.0**
- **Maven** để quản lý thư viện và build project

## Hướng dẫn cài đặt

1. **Clone dự án về máy:**
   ```bash
   git clone <link-repo>
   cd RailwayStationAutoTest-Group1
   ```

2. **Cài đặt các thư viện cần thiết:**
   ```bash
   mvn clean install
   ```

3. **Cấu hình WebDriver:**
   - Đảm bảo đã cài đặt ChromeDriver phù hợp với phiên bản Chrome trên máy.
   - Có thể cần chỉnh sửa đường dẫn ChromeDriver trong file code nếu cần.

## Cách chạy kiểm thử

- **Chạy toàn bộ test với Maven:**
  ```bash
  mvn test
  ```
- **Chạy từng class kiểm thử với IDE (IntelliJ, Eclipse, v.v):**
  - Mở file kiểm thử trong `src/test/java/Railway/`, nhấn chuột phải và chọn "Run".

## Danh sách các chức năng kiểm thử

Các ca kiểm thử tự động bao gồm:
- Đăng nhập/Đăng xuất
- Đăng ký tài khoản
- Quên mật khẩu
- Đổi mật khẩu
- Đặt vé tàu
- Xem lịch trình tàu
- Quản lý vé đã đặt

## Mô hình Page Object Model

- Mỗi trang web được đại diện bởi một class trong `src/main/java/PageObjects/Railway/`.
- Các thao tác với trang (nhập liệu, click, lấy thông báo,...) được đóng gói thành phương thức riêng biệt.

## Thành viên thực hiện
- Đinh Sỹ Quốc Doanh _ Leader
- Cao Văn Khánh Duy
- Nguyễn Hoàng
- Nguyễn Anh Tú

## Đóng góp

- Fork và tạo pull request nếu bạn muốn đóng góp.
- Vui lòng mô tả rõ ràng thay đổi và đảm bảo các test đều chạy thành công.
