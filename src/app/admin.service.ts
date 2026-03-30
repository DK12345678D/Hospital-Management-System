import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DashboardResponse } from './dashboard-response';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = `${environment.apiUrl}/api/admin`;

  constructor(private httpClient: HttpClient) { }

  getDashboardStats(): Observable<DashboardResponse> {
    return this.httpClient.get<DashboardResponse>(`${this.baseUrl}/dashboard`);
  }
}
