package com.bridgelabz.Bank_Application.dto;

import com.bridgelabz.Bank_Application.model.Estatements;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public @Data class EstatementLists {
    List<Estatements>depositedEstatement;
    List<Estatements>withDrawnListEstatement;
}
