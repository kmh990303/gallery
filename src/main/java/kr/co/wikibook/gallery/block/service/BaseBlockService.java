package kr.co.wikibook.gallery.block.service;

import kr.co.wikibook.gallery.block.entity.Block;
import kr.co.wikibook.gallery.block.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseBlockService implements BlockService {

    private final BlockRepository blockRepository;

    @Override
    public void add(String token) {
        blockRepository.save(new Block(token));
    }

    @Override
    public boolean has(String token) {
        return blockRepository.findByToken(token).isPresent();
    }
}
