# ProHangoutServer

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

o	Thay đổi avatar của tài khoản, avatar của từng user sẽ được lưu trữ theo ví dụ: 
http://s2bzen.me/theanhdz/images/ntheanh201.png, trong đó ntheanh201 là tên user của mình ^^.

-	Cách sử dụng:
o	Mở thư mục ProHangout/dist và chạy file ProHangout.jar.
o	Đăng ký tài khoản -> Đăng nhập -> Chat :v. Khi chat xong mọi người hãy chat: /quit để người trong phòng biết được bạn đã thoát và để server hoạt động đúng như mong muốn, tránh tình trạng tắt đột ngột T.T
o	Để tìm kiếm bạn bè trong phòng chat, sử dụng: http://s2bzen.me
## For Developers
(Sẽ được update trong thời gian sớm nhất để mọi người có thể tự tạo server local hoặc upload lên host sử dụng như 1 máy chủ)
