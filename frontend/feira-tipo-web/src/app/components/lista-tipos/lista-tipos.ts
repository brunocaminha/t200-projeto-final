import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TipoFeiraService } from '../../services/tipo-feira.service';
import { TipoFeira, TipoFeiraRequest } from '../../models';

/** Lista, cadastra e remove tipos de feira; remover um tipo em uso exibe o 422. */
@Component({
  selector: 'app-lista-tipos',
  imports: [FormsModule],
  templateUrl: './lista-tipos.html',
  styleUrl: './lista-tipos.css'
})
export class ListaTiposComponent implements OnInit {

  tipos = signal<TipoFeira[]>([]);
  carregando = signal(true);
  sucesso = signal<string | null>(null);
  erro = signal<string | null>(null);
  erroNome = signal<string | null>(null);
  salvando = signal(false);

  novo: TipoFeiraRequest = { nome: '' };

  constructor(private tipoFeiraService: TipoFeiraService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando.set(true);
    this.tipoFeiraService.listar().subscribe({
      next: (dados) => {
        this.tipos.set(dados);
        this.carregando.set(false);
      },
      error: () => {
        this.erro.set('Não foi possível carregar os tipos. O backend está no ar?');
        this.carregando.set(false);
      }
    });
  }

  cadastrar(): void {
    this.limparMensagens();
    this.salvando.set(true);
    this.tipoFeiraService.salvar(this.novo).subscribe({
      next: (criado) => {
        this.salvando.set(false);
        this.sucesso.set(`Tipo "${criado.nome}" cadastrado.`);
        this.novo = { nome: '' };
        this.carregar();
      },
      error: (resp) => {
        this.salvando.set(false);
        this.tratarErro(resp);
      }
    });
  }

  remover(tipo: TipoFeira): void {
    if (!confirm(`Remover o tipo "${tipo.nome}"?`)) {
      return;
    }
    this.limparMensagens();
    this.tipoFeiraService.remover(tipo.id).subscribe({
      next: () => {
        this.sucesso.set(`Tipo "${tipo.nome}" removido.`);
        this.carregar();
      },
      error: (resp) => this.tratarErro(resp)
    });
  }

  private limparMensagens(): void {
    this.sucesso.set(null);
    this.erro.set(null);
    this.erroNome.set(null);
  }

  /** 400 destaca o campo nome; 422 mostra a regra (ex.: tipo em uso). */
  private tratarErro(resp: { status?: number; error?: { message?: string; errors?: { campo: string; mensagem: string }[] } }): void {
    const corpo = resp?.error;
    if (resp?.status === 400 && Array.isArray(corpo?.errors)) {
      const nomeErro = corpo.errors.find((e) => e.campo === 'nome');
      this.erroNome.set(nomeErro?.mensagem ?? 'Dados inválidos.');
    } else if (resp?.status === 422) {
      this.erro.set(corpo?.message ?? 'Violação de regra de negócio.');
    } else {
      this.erro.set('Erro inesperado.');
    }
  }
}
