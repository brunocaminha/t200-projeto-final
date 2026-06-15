import { Routes } from '@angular/router';
import { ListaFeirasComponent } from './components/lista-feiras/lista-feiras';

export const routes: Routes = [
  { path: '', redirectTo: 'feiras', pathMatch: 'full' },
  { path: 'feiras', component: ListaFeirasComponent },
  { path: '**', redirectTo: 'feiras' }
];
