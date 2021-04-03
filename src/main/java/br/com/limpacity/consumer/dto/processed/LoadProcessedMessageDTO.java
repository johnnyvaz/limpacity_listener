package br.com.limpacity.consumer.dto.processed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoadProcessedMessageDTO    {

    private String uuid;

    private Set<Long> insertedColetaIds;

}
