import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  templateUrl: './downloads.component.html'
})
export class DownloadsComponent {
  builds: any;
  buildsUrl = '/api/v1/downloads/';
  loading = true;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.getBuilds();
  }

  getBuilds(): void {
    this.http
        .get<any[]>(this.buildsUrl)
        .subscribe(builds => {
          this.builds = builds;
          this.loading = false;
        })
  }
}
