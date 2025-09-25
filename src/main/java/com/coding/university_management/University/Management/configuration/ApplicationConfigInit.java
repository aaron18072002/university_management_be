package com.coding.university_management.University.Management.configuration;

import com.coding.university_management.University.Management.entity.*;
import com.coding.university_management.University.Management.enums.TenTinChi;
import com.coding.university_management.University.Management.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationConfigInit {

    PasswordEncoder passwordEncoder;
    TransactionTemplate transactionTemplate; // added

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";
    @NonFinal
    static final String ADMIN_PASSWORD = "12345678";

    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository,
                                               RoleRepository roleRepository,
                                               PermissionRepository permissionRepository,
                                               LoaiTinChiRepository loaiTinChiRepository,
                                               NganhHocRepository nganhHocRepository,
                                               MonHocRepository monHocRepository,
                                               TinChiRepository tinChiRepository) {

        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                Map<String, String> permissions = Map.ofEntries(
                        Map.entry("student:read", "Xem thông tin sinh viên"),
                        Map.entry("student:create", "Tạo sinh viên"),
                        Map.entry("student:update", "Cập nhật sinh viên"),
                        Map.entry("student:delete", "Xóa sinh viên"),
                        Map.entry("course:read", "Xem thông tin môn học"),
                        Map.entry("course:create", "Tạo môn học"),
                        Map.entry("course:update", "Cập nhật môn học"),
                        Map.entry("course:delete", "Xóa môn học"),
                        Map.entry("schedule:read", "Xem lịch học"),
                        Map.entry("schedule:create", "Tạo lịch học"),
                        Map.entry("schedule:update", "Cập nhật lịch học"),
                        Map.entry("schedule:delete", "Xóa lịch học"),
                        Map.entry("grade:read", "Xem điểm"),
                        Map.entry("grade:update", "Cập nhật điểm"),
                        Map.entry("tuition:read", "Xem học phí"),
                        Map.entry("tuition:create", "Tạo học phí"),
                        Map.entry("tuition:update", "Cập nhật học phí"),
                        Map.entry("credit:register", "Quyền đăng ký tín chỉ"),
                        Map.entry("credit:cancel", "Quyền hủy tín chỉ")
                );
                permissions.forEach((n, d) -> {
                    if (permissionRepository.findById(n).isEmpty()) {
                        permissionRepository.save(Permission.builder().name(n).description(d).build());
                    }
                });

                Set<Permission> studentPermissions = Set.of("student:read", "course:read", "schedule:read", "grade:read", "tuition:read", "credit:register", "credit:cancel")
                        .stream().map(permissionRepository::findById).map(o -> o.orElse(null)).collect(Collectors.toSet());
                roleRepository.save(Role.builder().name("SINHVIEN").description("Vai trò sinh viên").permissions(studentPermissions).build());

                Set<Permission> teacherPermissions = Set.of("student:read", "course:read", "schedule:read", "grade:read", "grade:update")
                        .stream().map(permissionRepository::findById).map(o -> o.orElse(null)).collect(Collectors.toSet());
                roleRepository.save(Role.builder().name("GIAOVIEN").description("Vai trò giáo viên").permissions(teacherPermissions).build());

                Set<Permission> accountantPermissions = Set.of("student:read", "tuition:read", "tuition:create", "tuition:update")
                        .stream().map(permissionRepository::findById).map(o -> o.orElse(null)).collect(Collectors.toSet());
                roleRepository.save(Role.builder().name("KETOAN").description("Vai trò kế toán").permissions(accountantPermissions).build());

                Set<Permission> adminPermissions = new HashSet<>(permissionRepository.findAll());
                Role adminRole = roleRepository.save(Role.builder().name("QUANTRIVIEN").description("Vai trò quản trị viên").permissions(adminPermissions).build());

                User user = User.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(Set.of(adminRole))
                        .build();
                userRepository.save(user);

                String lyThuyetId = "LT-" + UUID.randomUUID().toString().substring(0, 8);
                String thucHanhId = "TH-" + UUID.randomUUID().toString().substring(0, 8);
                if (loaiTinChiRepository.count() == 0) {
                    LoaiTinChi lt = LoaiTinChi.builder().maLoaiTinChi(lyThuyetId).tenLoaiTinChi("Lý thuyết").build();
                    LoaiTinChi th = LoaiTinChi.builder().maLoaiTinChi(thucHanhId).tenLoaiTinChi("Thực hành").build();
                    loaiTinChiRepository.saveAll(List.of(lt, th));
                }

                if (!nganhHocRepository.existsByTenNganhHoc("Công nghệ phần mềm")) {
                    NganhHoc cnpm = NganhHoc.builder()
                            .tenNganhHoc("Công nghệ phần mềm")
                            .moTa("Ngành học về kỹ thuật phát triển phần mềm")
                            .build();
                    nganhHocRepository.save(cnpm);
                }

                if (!monHocRepository.existsByTenMonHoc("Lập trình Java")) {
                    MonHoc java = MonHoc.builder()
                            .maMonHoc("JAVA-" + UUID.randomUUID().toString().substring(0, 8))
                            .tenMonHoc("Lập trình Java")
                            .moTa("Môn học về ngôn ngữ lập trình Java")
                            .build();
                    monHocRepository.save(java);

                    MonHoc db = MonHoc.builder()
                            .maMonHoc("DB-" + UUID.randomUUID().toString().substring(0, 8))
                            .tenMonHoc("Hệ quản trị cơ sở dữ liệu")
                            .moTa("Môn học về thiết kế và quản lý cơ sở dữ liệu")
                            .build();
                    monHocRepository.save(db);

                    MonHoc web = MonHoc.builder()
                            .maMonHoc("WEB-" + UUID.randomUUID().toString().substring(0, 8))
                            .tenMonHoc("Lập trình Web")
                            .moTa("Môn học về phát triển ứng dụng web")
                            .build();
                    monHocRepository.save(web);
                }
            }

            // Always ensure majors + 10 courses + mapping (inside one transaction)
            transactionTemplate.execute(status -> {
                initMajorsAndCoursesWithMapping(nganhHocRepository, monHocRepository);
                return null;
            });

            log.info("Initialization finished.");
        };
    }

    // Creates (if absent) 2 majors + 10 fixed courses and maps them (owning side: NganhHoc.monHocs).
    private void initMajorsAndCoursesWithMapping(NganhHocRepository nganhHocRepository,
                                                 MonHocRepository monHocRepository) {

        NganhHoc cnpm = nganhHocRepository.findByTenNganhHoc("Công nghệ phần mềm")
                .orElseGet(() -> nganhHocRepository.save(
                        NganhHoc.builder()
                                .tenNganhHoc("Công nghệ phần mềm")
                                .moTa("Ngành học về phát triển phần mềm")
                                .build()
                ));

        NganhHoc ketoan = nganhHocRepository.findByTenNganhHoc("Kế toán")
                .orElseGet(() -> nganhHocRepository.save(
                        NganhHoc.builder()
                                .tenNganhHoc("Kế toán")
                                .moTa("Ngành học về kế toán và tài chính")
                                .build()
                ));

        record Def(String code, String name, String desc, String belong) {}
        List<Def> defs = List.of(
                new Def("JV101","Lập trình Java","Ngôn ngữ lập trình Java cơ bản","CNPM"),
                new Def("DSA102","Cấu trúc dữ liệu","Cấu trúc và giải thuật","CNPM"),
                new Def("DB103","Cơ sở dữ liệu","Mô hình dữ liệu & SQL","CNPM"),
                new Def("WEB104","Phát triển Web","Xây dựng ứng dụng Web","CNPM"),
                new Def("TEST105","Kiểm thử phần mềm","Kỹ thuật kiểm thử","CNPM"),
                new Def("ACC201","Kế toán cơ bản","Nguyên lý kế toán","KETOAN"),
                new Def("ACC202","Kế toán tài chính","Báo cáo & chuẩn mực","KETOAN"),
                new Def("TAX203","Thuế doanh nghiệp","Quản lý và kê khai thuế","KETOAN"),
                new Def("STAT301","Thống kê ứng dụng","Phân tích thống kê","BOTH"),
                new Def("PM302","Quản trị dự án","Quản lý vòng đời dự án","BOTH")
        );

        Map<String, MonHoc> cache = new HashMap<>();
        for (Def d : defs) {
            MonHoc mon = monHocRepository.findById(d.code()).orElseGet(() -> {
                MonHoc m = MonHoc.builder()
                        .maMonHoc(d.code())
                        .tenMonHoc(d.name())
                        .moTa(d.desc())
                        .build();
                return monHocRepository.save(m);
            });
            cache.put(d.code(), mon);
        }

        if (cnpm.getMonHocs() == null) cnpm.setMonHocs(new HashSet<>());
        if (ketoan.getMonHocs() == null) ketoan.setMonHocs(new HashSet<>());

        // Optional idempotent clean: remove only those 10 codes before re-adding
        cnpm.getMonHocs().removeIf(m -> cache.containsKey(m.getMaMonHoc()));
        ketoan.getMonHocs().removeIf(m -> cache.containsKey(m.getMaMonHoc()));

        for (Def d : defs) {
            MonHoc mon = cache.get(d.code());
            switch (d.belong()) {
                case "CNPM" -> cnpm.getMonHocs().add(mon);
                case "KETOAN" -> ketoan.getMonHocs().add(mon);
                case "BOTH" -> {
                    cnpm.getMonHocs().add(mon);
                    ketoan.getMonHocs().add(mon);
                }
            }
        }

        nganhHocRepository.saveAll(List.of(cnpm, ketoan)); // owning side persisted
        log.info("Majors mapped: CNPM={} courses, KETOAN={} courses.",
                cnpm.getMonHocs().size(), ketoan.getMonHocs().size());
    }
}