import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { TipoFeira } from '../models';

/** Consome os endpoints REST de tipos de feira (usado para popular o combo). */
@Injectable({ providedIn: 'root' })
export class TipoFeiraService {

  private apiUrl = `${environment.apiUrl}/tipos-feira`;

  constructor(private http: HttpClient) {}

  listar(): Observable<TipoFeira[]> {
    return this.http.get<TipoFeira[]>(this.apiUrl);
  }
}
