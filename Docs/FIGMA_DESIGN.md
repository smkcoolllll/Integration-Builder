Design Overview
A modern, intuitive dashboard interface for integrating with any SaaS application. The design emphasizes simplicity, safety, and guided workflows.

Screen 1: Dashboard/Home

LAYOUT:
┌─────────────────────────────────────────┐
│ CloudEagle Integration Builder  [⚙️]     │
├─────────────────────────────────────────┤
│ Welcome back, Alice!                    │
│ Manage your SaaS integrations easily    │
├─────────────────────────────────────────┤
│ Your Integrations              [+ New]  │
│                                         │
│ ┌─────────────┐  ┌─────────────┐      │
│ │  Calendly   │  │  Dropbox    │      │
│ │ ✓ Connected │  │ ✓ Connected │      │
│ │ 2 envs      │  │ 1 env       │      │
│ │ [Config]    │  │ [Config]    │      │
│ └─────────────┘  └─────────────┘      │
│                                         │
├─────────────────────────────────────────┤
│ Recent Activity                         │
│ • Synced 45 users from Calendly (2h)   │
│ • Updated field mapping (1d)            │
│ • Fetched 200 users from Dropbox (3d)  │
└─────────────────────────────────────────┘

COLORS:
- Primary Blue: #2563EB
- Success Green: #10B981
- Warning Orange: #F59E0B
- Background: #F3F4F6

Screen 2: Create/Edit Configuration

LAYOUT:
┌─────────────────────────────────────────┐
│ Configure Calendly Integration [← Back] │
├─────────────────────────────────────────┤
│ Environment: [Sandbox ▼] [Production ▼]│
│                                         │
│ SECTION 1: Basic Configuration          │
│ Application: calendly (read-only)       │
│ Base URL: [https://api.calendly.com]   │
│ Endpoint: [/users]                      │
│ HTTP Method: [GET ▼]                    │
│                                         │
│ SECTION 2: Authentication               │
│ Auth Type: [Bearer Token ▼]             │
│ Token: [••••••••••••••] [Show] [Reset]  │
│ Token Source: [Environment Variable ▼] │
│                                         │
│ SECTION 3: Field Mapping                │
│ API Field      →  Standard Field        │
│ email          →  email ✓               │
│ name           →  name ✓                │
│ id             →  externalUserId ✓      │
│ [+ Add Mapping]                         │
│                                         │
│ SECTION 4: Test Connection              │
│ [Test Connection]                       │
│ Status: ✓ Connection successful (245ms) │
│                                         │
│                    [Cancel]  [Save]     │
└─────────────────────────────────────────┘

DESIGN FEATURES:
- Collapsible sections
- Real-time validation
- Token masking for security
- Visual feedback on connection test

Screen 3: Fetch Users (Sandbox Testing)

LAYOUT:
┌─────────────────────────────────────────┐
│ Fetch Users from Calendly     [← Back]  │
├─────────────────────────────────────────┤
│ Environment: [◉ Sandbox  ◯ Production] │
│ Mode:        [◉ Test ◯ Live]            │
│                                         │
│ ⚠️ SANDBOX MODE (Safe Testing)          │
│ Perfect for testing integrations        │
│ Changes won't affect production         │
│                                         │
│ OPTIONS:                                │
│ ☑ Auto-map fields                       │
│ ☑ Store raw API response                │
│ ☑ Validate required fields              │
│ Max Records: [100 ▼]                    │
│                                         │
│                    [Cancel] [Fetch]     │
│                                         │
├─────────────────────────────────────────┤
│ RESULTS                                 │
│ Status: ✓ Successfully fetched 45 users │
│ Time: 3.2 seconds                       │
│                                         │
│ ┌─────────────────────────────────────┐│
│ │ Sample Data                         ││
│ │ Email          │ Name    │ Status  ││
│ ├─────────────────────────────────────┤│
│ │ john@ex.com    │ John    │ active  ││
│ │ jane@ex.com    │ Jane    │ active  ││
│ │ bob@ex.com     │ Bob     │ inactive││
│ └─────────────────────────────────────┘│
│                                         │
│ [Download CSV] [View JSON]              │
│                                         │
│ ☑ Data looks good                       │
│ ☑ Field mappings correct                │
│ ☑ Ready for production                  │
│                                         │
│ Next: Switch to PRODUCTION when ready   │
└─────────────────────────────────────────┘

KEY FEATURE: Sandbox First Workflow!

Screen 4: View Fetched Users

LAYOUT:
┌─────────────────────────────────────────┐
│ Users from Calendly      Sandbox | Prod │
├─────────────────────────────────────────┤
│ [Search] [Status: All ▼] [Date Range ▼]│
│                                         │
│ ┌─────────────────────────────────────┐│
│ │ Email        │ Name    │ Status  │ ││
│ ├─────────────────────────────────────┤│
│ │ alice@t.com  │ Alice   │ active  │ ││
│ │ bob@t.com    │ Bob     │ active  │ ││
│ │ charlie@t.c  │ Charlie │ inactive│ ││
│ │ diana@t.com  │ Diana   │ active  │ ││
│ │ eve@t.com    │ Eve     │ active  │ ││
│ │                                   │ ││
│ │ [< Prev] Page 1 of 5 [Next >]    │ ││
│ └─────────────────────────────────────┘│
│                                         │
│ Summary:                                │
│ Total: 245 | Active: 198 (80.8%)       │
│ Inactive: 47 (19.2%)                    │
│ Last Updated: 2h ago                    │
│                                         │
│ [Export CSV] [Export JSON] [Refresh]   │
└─────────────────────────────────────────┘

FEATURES:
- Real-time search
- Filtering options
- Pagination
- Export functionality

Screen 5: Monitoring & Logs

LAYOUT:
┌─────────────────────────────────────────┐
│ Integration Monitoring      [Settings]   │
├─────────────────────────────────────────┤
│ ┌────────────┐  ┌────────────┐         │
│ │ Last 24H   │  │ Success    │         │
│ │ 1,234 Calls│  │ Rate: 98.5%│         │
│ └────────────┘  └────────────┘         │
│                                         │
│ Activity Log:                           │
│ ✓ 2025-12-13 14:30  Calendly (Sandbox) │
│   Fetched 45 users in 3.2s              │
│                                         │
│ ✓ 2025-12-13 12:00  Dropbox (Prod)     │
│   Fetched 120 users in 5.1s             │
│                                         │
│ ✗ 2025-12-13 10:15  Calendly (Prod)    │
│   Error: Invalid token (401)            │
│   [Update Token] [Retry]                │
│                                         │
│ ✓ 2025-12-13 09:45  Config Updated     │
│   Updated field mappings                │
│                                         │
│ [Show More] [Export Log]                │
└─────────────────────────────────────────┘

FEATURES:
- Real-time metrics
- Error tracking
- Success/failure statistics
- Easy troubleshooting

Screen 6: Settings & Security

LAYOUT:
┌─────────────────────────────────────────┐
│ Settings                      [← Back]   │
├─────────────────────────────────────────┤
│ INTEGRATIONS                            │
│ ☑ Enable Sandbox Mode                   │
│ ☑ Auto-sync on schedule                 │
│ ☑ Store raw API responses               │
│ Frequency: [Daily ▼]                    │
│ Time: [02:00 UTC ▼]                     │
│                                         │
│ SECURITY                                │
│ ☑ Require HTTPS for API calls           │
│ ☑ Encrypt sensitive fields              │
│ ☑ Rate limiting (500 calls/hour)        │
│                                         │
│ DATA RETENTION                          │
│ Keep Sandbox data: [90 days ▼]          │
│ Keep Production data: [1 year ▼]        │
│ [Clear Old Data] [Export Data]          │
│                                         │
│ [Help] [API Reference] [Report Issue]   │
└─────────────────────────────────────────┘

KEY FEATURES:
- Safety-first settings
- Data retention policies
- Security options

Key Design Principles:

Safety First - Sandbox prominently displayed
Clear Feedback - Every action shows result
Transparent Data - Show both raw and mapped data
Progressive Disclosure - Advanced options hidden
Error Prevention - Confirmations for destructive actions