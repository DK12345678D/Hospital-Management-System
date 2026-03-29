import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AdminauthService {

  private baseUrl = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  authenticate(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/Admin/SignIn`, { identifier: email, password }).pipe(
      tap(res => {
        if (res && res.data && res.data.token) {
          sessionStorage.setItem('token', res.data.token);
          sessionStorage.setItem('role', 'ADMIN');
          sessionStorage.setItem('email', res.data.email);
        }
      })
    );
  }

  isUserLoggedIn() {
    return !!sessionStorage.getItem('token') && sessionStorage.getItem('role') === 'ADMIN';
  }

  logOut() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('role');
    sessionStorage.removeItem('email');
  }
}
