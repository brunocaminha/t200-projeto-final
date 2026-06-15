import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Feira, FeiraRequest } from '../models';

/** Consome os endpoints REST de feiras no backend Spring Boot. */
@Injectable({ providedIn: 'root' })
export class FeiraService {

  private apiUrl = `${environment.apiUrl}/feiras`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Feira[]> {
    return this.http.get<Feira[]>(this.apiUrl);
  }

  salvar(feira: FeiraRequest): Observable<Feira> {
    return this.http.post<Feira>(this.apiUrl, feira);
  }

  remover(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
