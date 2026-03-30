import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html'
})
export class ForgotPasswordComponent implements OnInit {

  email = '';
  phone = '';
  resetMethod = 'email'; // Default

  constructor(private router: Router, private authService: AuthenticationService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    const identifier = this.resetMethod === 'email' ? this.email : this.phone;
    
    // Using existing forgotPassword method (assumes it handles identifier string)
    this.authService.forgotPassword(identifier).subscribe({
      next: (res) => {
        alert("Reset OTP sent to your registered contact node!");
        this.router.navigate(['reset-password'], { 
          queryParams: { 
            identifier: identifier, 
            method: this.resetMethod 
          } 
        });
      },
      error: (err) => {
        alert("Error: " + (err.error?.message || "Identifier not found in system"));
      }
    });
  }
}
