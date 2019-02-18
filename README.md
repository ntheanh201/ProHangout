# ProHangoutServer

## For Normal Users

Tên ứng dụng: ProHangout.

Mô tả về sản phẩm:
-	Sản phẩm được tạo ra với mục đích là các thành viên trong CLB Lập trình PTIT và tất cả mọi người có thể gửi trò chuyện, chém gió, … với nhau trong một không gian khác bên ngoài các mạng xã hội.
-	Để tránh những lo ngại về ăn cắp dữ liệu hoặc lấy nội dung trong các cuộc trò chuyện tạo quảng cáo, ProHangout sẽ không lưu giữ bất cứ nội dung chat nào trên server, giữ cho các đoạn hội thoại của mọi người đảm bảo chỉ những ai kết nối tới server trong cùng thời gian mới có thể thấy được cuộc trò chuyện của nhau.
-	Ứng dụng áp dụng công nghệ Socket giúp cho việc nhắn tin giữa các user được realtime (phản hồi lại ngay lập tức khi có tin nhắn đến), upload avatar của từng user lên server. Thêm thư viện JDBC Connector để kết nối tới hệ thống database lưu trữ thông tin về tài khoản, mật khẩu giúp cho việc định danh mỗi người khi kết nối tới ProHangout.
-	Các tính năng chính:

    o	Đăng nhập, đăng kí tài khoản.

    o	Trò chuyện, có thể trò chuyện dùng tiếng Anh hoặc tiếng Việt mà không bị lỗi font chữ.

    o	Trò chuyện sử dụng hệ thống emoji có sẵn.

    o	Thông báo (pop-up) tới user mỗi khi có tin nhắn mới, pop-up xuất hiện trong thời gian 3.5 giây.

    o	Thông báo trên khung chat nếu có người vào/thoát room.

    o	Thay đổi mật khẩu tài khoản

    o	Thay đổi avatar của tài khoản, avatar của từng user sẽ được lưu trữ theo ví dụ: http://s2bzen.me/theanhdz/images/ntheanh201.png, trong đó ntheanh201 là tên user của mình ^^.

-	Cách sử dụng:
o	Mở thư mục ProHangout/dist và chạy file ProHangout.jar.
o	Đăng ký tài khoản -> Đăng nhập -> Chat :v. Khi chat xong mọi người hãy chat: /quit để người trong phòng biết được bạn đã thoát và để server hoạt động đúng như mong muốn, tránh tình trạng tắt đột ngột T.T
o	Để tìm kiếm bạn bè trong phòng chat, sử dụng: http://s2bzen.me

## For Developers

Mô tả về cách hoạt động:
-   Có 2 thư mục là ChatServer và UploadAvatar dùng để chạy trên Server.
-   App sẽ kết nối tới MySQL đặt trên Server của http://s2bzen.me
-   App ChatServer có tác dụng dùng để mở port socket (2222) trên server và phản hồi lại các request từ phía client nội dung chat của các users.
-   App UploadAvatar dùng để mở port socket trên server (4444), phản hồi lại các yêu cầu thay đổi avatar của users.

Config:
-   Các mục cần thay đổi chủ yếu nằm ở app ProHangout, các app trên Server các bạn có thể tùy chính, hoặc không cần vì nó dựa vào kết nối của mình tới. Mình sẽ hướng dẫn các bạn config trên localhost trước để các bạn có thể hiểu rõ mình đang làm gì, và từ đó có thể áp dụng trên chính server online của mình.
-   Đầu tiên hãy thay đổi địa chỉ kết nối tới MySQL của bạn, trên MySQL mình dùng DATABASE có tên prohangout, TABLE tên là: users. Các bạn cần cài đặt và biết sử dụng MySQL
    o   Mở file Connect.java trong Prohangout tìm đến đoạn:
        ```String hostName = "xxx";
        String dbName = "xxx";
        String userName = "xxx";
        String password = "xxx";
        ```
        thay đổi hostName thành localhost, sửa dbName thành tên database của bạn, userName thành tên đăng nhập và password.
-   Khi xong vấn đề với database, tiếp theo sẽ là phần xử lí bên trong ứng dụng chat. Để chạy ứng dụng trên localhost, chạy app ChatServer, mở file ClientServer.java trong ProHangout và thay
        String IPServer = "172.104.63.169";
        thành String IPServer = "localhost";   
-   Tiếp theo để chạy tính năng Upload Avatar trên localhost, chạy app Upload Avatar, mở file File Client trong ProHangout/UploadFile và thay
        sock = new Socket("172.104.63.169", 4444);
        thành sock = new Socket("localhost", 4444);
-   Lưu ý: có thể thay port socket bằng port khác, mình sử dụng port 2222 và 4444, bạn có thể thay bằng port tùy ý nhưng hãy đảm bảo trên Client và Server phải cùng 1 port kết nối.
