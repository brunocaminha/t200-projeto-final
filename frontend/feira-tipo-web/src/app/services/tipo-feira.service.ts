import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { TipoFeira, TipoFeiraRequest } from '../models';

/** Consome os endpoints REST de tipos de feira. */
@Injectable({ providedIn: 'root' })
export class TipoFeiraService {

  private apiUrl = `${environment.apiUrl}/tipos-feira`;

  constructor(private http: HttpClient) {}

  listar(): Observable<TipoFeira[]> {
    return this.http.get<TipoFeira[]>(this.apiUrl);
  }

  salvar(tipo: TipoFeiraRequest): Observable<TipoFeira> {
    return this.http.post<TipoFeira>(this.apiUrl, tipo);
  }

  remover(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
