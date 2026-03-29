import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private baseUrl = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) { }

  loginDoctor(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/Doctor/SignIn`, { identifier: email, password }).pipe(
      tap(res => {
        if (res && res.data && res.data.token) {
          sessionStorage.setItem('token', res.data.token);
          sessionStorage.setItem('role', 'DOCTOR');
          sessionStorage.setItem('email', res.data.email);
        }
      })
    );
  }

  loginPatient(email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/Patient/SignIn`, { identifier: email, password }).pipe(
      tap(res => {
        if (res && res.data && res.data.token) {
          sessionStorage.setItem('token', res.data.token);
          sessionStorage.setItem('role', 'PATIENT');
          sessionStorage.setItem('email', res.data.email);
        }
      })
    );
  }

  isUserLoggedIn() {
    return !!sessionStorage.getItem('token');
  }

  getToken() {
    return sessionStorage.getItem('token');
  }

  logOut() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('role');
    sessionStorage.removeItem('email');
  }

  registerAdmin(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/Admin/SignUp`, user);
  }

  registerDoctor(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/Doctor/SignUp`, user);
  }

  registerPatient(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/Patient/SignUp`, user);
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/forgot-password`, { identifier: email });
  }

  resetPassword(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/reset-password`, data);
  }

  updateProfile(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/update-profile`, data);
  }

  // Backward compatibility for existing components if needed
  authenticate(username: string, password: string) {
    // This is problematic as it's synchronous. Components should be updated to used subscribe().
    return false; 
  }

}
