package kr.co.wikibook.gallery.block.service;

public interface BlockService {
    // 토큰 차단 데이터 삽입
    void add(String token);

    // 토큰 차단
    boolean has(String token);
}
