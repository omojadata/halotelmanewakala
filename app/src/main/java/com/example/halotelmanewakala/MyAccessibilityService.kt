package com.example.halotelmanewakala

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent


class MyAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val eventType = event.eventType
        var eventText: String? = null
        when (eventType) {
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> eventText = "Typed: "
        }
        eventText = eventText + event.text

        //print the typed text in the console. Or do anything you want here.
        println("ACCESSIBILITY SERVICE : $eventText")
    }

    override fun onInterrupt() {
        //whatever
    }

    public override fun onServiceConnected() {
        //configure our Accessibility service
        val info = serviceInfo
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN
        info.notificationTimeout = 100
        this.serviceInfo = info
    }
}