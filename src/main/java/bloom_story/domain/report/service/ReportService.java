package bloom_story.domain.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bloom_story.domain.report.dto.ReportResponse;
import bloom_story.domain.user.model.User;
import bloom_story.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;

    @Transactional
    public ReportResponse createReport(Integer userId) {
        User user = userRepository.getById(userId);
        //TODO: 사용자 조회 -> 최근 일주일치 스토리 가져오기 -> GPT 분석해서 요약 내용 받기 -> response에 담기
        return ReportResponse.from(null);
    }
}
