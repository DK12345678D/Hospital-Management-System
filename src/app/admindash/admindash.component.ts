import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Patient } from '../patient';
import { PatientService } from '../patient.service';
import { AdminService } from '../admin.service';
import { DashboardResponse } from '../dashboard-response';
import { ReportService } from '../report.service';

@Component({
  selector: 'app-admindash',
  templateUrl: './admindash.component.html'
})
export class AdmindashComponent implements OnInit {

  searchText: string = '';
  patients: Patient[] = []; 
  stats: DashboardResponse | null = null;
  loading: boolean = false;

  constructor(private patientService: PatientService,
    private adminService: AdminService,
    private router: Router,
    private reportService: ReportService) { }

  ngOnInit(): void {
    this.getPatients();
    this.getStats();
  }

  getStats() {
    this.adminService.getDashboardStats().subscribe(data => {
      this.stats = data;
    });
  }

  private getPatients(){
    this.patientService.getPatientslist().subscribe(data => { this.patients = data; 
    });
  }

  updatePatient(id: number) {
    this.router.navigate(['updatepatient', id]);
  }

  deletePatient(id: number) {
    this.patientService.deletePatient(id).subscribe(data => {
      console.log(data);
      this.getPatients();
    } ); 
  }

  triggerUpload() {
    document.getElementById('fileInput')?.click();
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.loading = true;
      this.reportService.upload(file).subscribe(
        res => {
          this.loading = false;
          alert('Digital record added to system node successfully.');
          this.getPatients(); // Refresh view
        },
        err => {
          this.loading = false;
          alert('Network Error: Could not synchronize record.');
        }
      );
    }
  }

  viewPatient(id: number) {
    this.router.navigate(['viewpatient', id]);
  }

  openReport(filename: string) {
     this.reportService.openReport(filename);
  }
}
