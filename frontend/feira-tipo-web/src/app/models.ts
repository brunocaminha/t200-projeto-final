/** Espelha os DTOs de resposta do backend. */
export interface TipoFeira {
  id: string;
  nome: string;
}

export interface Feira {
  id: string;
  nome: string;
  logradouro: string;
  bairro: string;
  ativa: boolean;
  tipoId: string;
  tipoNome: string;
}

/** Corpo enviado ao cadastrar uma feira. */
export interface FeiraRequest {
  nome: string;
  logradouro: string;
  bairro: string;
  ativa: boolean;
  tipoId: string;
}

/** Corpo enviado ao cadastrar um tipo de feira. */
export interface TipoFeiraRequest {
  nome: string;
}
