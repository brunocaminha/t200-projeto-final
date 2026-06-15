import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FeiraService } from '../../services/feira.service';
import { Feira } from '../../models';

/** Lista as feiras cadastradas e permite removê-las. */
@Component({
  selector: 'app-lista-feiras',
  imports: [RouterLink],
  templateUrl: './lista-feiras.html',
  styleUrl: './lista-feiras.css'
})
export class ListaFeirasComponent implements OnInit {

  feiras = signal<Feira[]>([]);
  carregando = signal(true);
  erro = signal<string | null>(null);

  constructor(private feiraService: FeiraService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando.set(true);
    this.erro.set(null);
    this.feiraService.listar().subscribe({
      next: (dados) => {
        this.feiras.set(dados);
        this.carregando.set(false);
      },
      error: () => {
        this.erro.set('Não foi possível carregar as feiras. O backend está no ar?');
        this.carregando.set(false);
      }
    });
  }

  remover(feira: Feira): void {
    if (!confirm(`Remover a feira "${feira.nome}"?`)) {
      return;
    }
    this.feiraService.remover(feira.id).subscribe({
      next: () => this.carregar(),
      error: () => this.erro.set('Não foi possível remover a feira.')
    });
  }
}
