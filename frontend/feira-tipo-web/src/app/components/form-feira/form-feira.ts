import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { FeiraService } from '../../services/feira.service';
import { TipoFeiraService } from '../../services/tipo-feira.service';
import { TipoFeira, FeiraRequest } from '../../models';

/** Formulário de cadastro de feira, com combo de tipos e exibição de erros 400/422. */
@Component({
  selector: 'app-form-feira',
  imports: [FormsModule, RouterLink],
  templateUrl: './form-feira.html',
  styleUrl: './form-feira.css'
})
export class FormFeiraComponent implements OnInit {

  tipos = signal<TipoFeira[]>([]);
  sucesso = signal<string | null>(null);
  erroGeral = signal<string | null>(null);
  errosCampos = signal<Record<string, string>>({});
  salvando = signal(false);

  feira: FeiraRequest = { nome: '', logradouro: '', bairro: '', ativa: true, tipoId: '' };

  constructor(
    private feiraService: FeiraService,
    private tipoFeiraService: TipoFeiraService
  ) {}

  ngOnInit(): void {
    this.tipoFeiraService.listar().subscribe({
      next: (dados) => this.tipos.set(dados),
      error: () => this.erroGeral.set('Não foi possível carregar os tipos de feira.')
    });
  }

  salvar(): void {
    this.sucesso.set(null);
    this.erroGeral.set(null);
    this.errosCampos.set({});
    this.salvando.set(true);
    this.feiraService.salvar(this.feira).subscribe({
      next: (criada) => {
        this.salvando.set(false);
        this.sucesso.set(`Feira "${criada.nome}" cadastrada com sucesso.`);
        this.feira = { nome: '', logradouro: '', bairro: '', ativa: true, tipoId: '' };
      },
      error: (resp) => {
        this.salvando.set(false);
        this.tratarErro(resp);
      }
    });
  }

  /** Traduz o corpo de erro da API: 400 destaca campos, 422 mostra a regra violada. */
  private tratarErro(resp: { status?: number; error?: { message?: string; errors?: { campo: string; mensagem: string }[] } }): void {
    const corpo = resp?.error;
    if (resp?.status === 400 && Array.isArray(corpo?.errors)) {
      const mapa: Record<string, string> = {};
      for (const e of corpo.errors) {
        mapa[e.campo] = e.mensagem;
      }
      this.errosCampos.set(mapa);
      this.erroGeral.set('Corrija os campos destacados.');
    } else if (resp?.status === 422) {
      this.erroGeral.set(corpo?.message ?? 'Violação de regra de negócio.');
    } else {
      this.erroGeral.set('Erro inesperado ao cadastrar a feira.');
    }
  }
}
