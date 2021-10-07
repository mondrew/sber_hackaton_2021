package com.example.blockchain.dto;

import com.example.blockchain.model.Block;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChainDto {
    MetaDto meta;
    List<Block> chain;
}
