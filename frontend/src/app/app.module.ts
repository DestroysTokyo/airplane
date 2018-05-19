import {BrowserModule, Title} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {TimeAgoPipe} from 'time-ago-pipe';

import {AppComponent} from './component/app.component';
import {AppRoutingModule} from './app-routing.module';
import {DownloadsComponent} from './component/downloads/downloads.component';
import {HomeComponent} from "./component/home/home.component";

@NgModule({
  bootstrap: [AppComponent],
  declarations: [
    AppComponent,
    DownloadsComponent,
    HomeComponent,
    TimeAgoPipe
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    Title
  ]
})
export class AppModule {
}
